package com.github.javahello.erm.generator.core.api;

import com.github.javahello.erm.generator.core.codegen.ddl.BaseOutDDL;
import com.github.javahello.erm.generator.core.codegen.ddl.DbType;
import com.github.javahello.erm.generator.core.codegen.ddl.ICovDDL;
import com.github.javahello.erm.generator.core.codegen.ddl.OutDDLFactory;
import com.github.javahello.erm.generator.core.internal.TableCache;
import com.github.javahello.erm.generator.core.model.ErmDiffEnv;
import com.github.javahello.erm.generator.core.model.diff.DiffTable;
import com.github.javahello.erm.generator.core.tbdiff.DefaultTableListDiffProcess;
import com.github.javahello.erm.generator.core.tbdiff.ITableListDiff;
import com.github.javahello.erm.generator.core.util.DiffHelper;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 比较差异输出 sql
 */
public class ErmCmpDDLGenerator extends AbstractGenerator {


    protected ITableListDiff tableListDiff = new DefaultTableListDiffProcess();
    protected Map<DbType, BaseOutDDL> outDDLMap = new LinkedHashMap<>();
    protected String allSql;
    protected BaseOutDDL currentOutDDL;

    private String allSqlFileName = "dbName_all.sql";
    private String modifyColumnSqlFileName = "dbName_modify_column.sql";
    private String modifyIndexSqlFileName = "dbName_modify_index.sql";
    private String modifyTableSqlFileName = "dbName_modify_table.sql";

    public ErmCmpDDLGenerator(ErmDiffEnv ermDiffEnv) {
        super(ermDiffEnv);
        allSqlFileName = allSqlFileName.replace("dbName", ermDiffEnv.getDbName());
        modifyColumnSqlFileName = modifyColumnSqlFileName.replace("dbName", ermDiffEnv.getDbName());
        modifyIndexSqlFileName = modifyIndexSqlFileName.replace("dbName", ermDiffEnv.getDbName());
        modifyTableSqlFileName = modifyTableSqlFileName.replace("dbName", ermDiffEnv.getDbName());
    }


    @Override
    protected void errorExec(Exception exception) {
        log.error("导出 SQL 异常", exception);
    }

    @Override
    protected void runExec() {
        Optional<List<DiffTable>> diffAllOpt = tableListDiff.diff(newCache.getTables(), oldCache.getTables());
        diffAllOpt.ifPresent(diffAll -> {
            this.initDiffProcess(diffAll);
            currentOutDDL = outDDLMap.get(ermDiffEnv.getDbType());
            if (currentOutDDL == null) {
                throw new IllegalArgumentException("数据库类型不支持: " + ermDiffEnv.getDbType());
            }
            allSql = currentOutDDL.covDDL();
        });
    }

    protected void initDiffProcess(List<DiffTable> diffAll) {
        BaseOutDDL genMysqlDDL =
                OutDDLFactory.of(dbType, newCache, diffAll).orElseThrow(() -> new IllegalArgumentException("非法db类型"));
        Optional.ofNullable(oldCache).ifPresent(genMysqlDDL::setOldTableCache);
        outDDLMap.put(genMysqlDDL.dbType(), genMysqlDDL);
        Optional.ofNullable(doInitDiffProcess(newCache, diffAll))
                .ifPresent(outList -> outList.forEach(out -> outDDLMap.put(out.dbType(), out)));
    }

    protected List<BaseOutDDL> doInitDiffProcess(TableCache newCache, List<DiffTable> diffAll) {
        return null;
    }


    @Override
    protected void doInitEnv() {
        super.doInitEnv();
    }


    @Override
    protected void afterExec() {
        super.afterExec();
        writeFileData();
    }

    protected void writeFileData() {
        Optional.ofNullable(allSql).filter(DiffHelper::isNotEmpty).ifPresent(sql -> writeFileData(outFile(allSqlFileName),
                sql));

        ICovDDL fix = currentOutDDL.fix();

        Optional.ofNullable(fix).map(ICovDDL::covDDL).filter(DiffHelper::isNotEmpty)
                .ifPresent(sql -> writeFileData(outFile(fixFileName(allSqlFileName)), sql));

        Optional.ofNullable(currentOutDDL)
                .map(BaseOutDDL::getModifyColumnSql)
                .map(Object::toString)
                .filter(DiffHelper::isNotEmpty)
                .ifPresent(ddl -> writeFileData(outFile(modifyColumnSqlFileName), ddl));

        Optional.ofNullable(currentOutDDL)
                .map(BaseOutDDL::getModifyIndexSql)
                .map(Object::toString)
                .filter(DiffHelper::isNotEmpty)
                .ifPresent(ddl -> writeFileData(outFile(modifyIndexSqlFileName), ddl));

        Optional.ofNullable(currentOutDDL)
                .map(BaseOutDDL::getModifyTableSql)
                .map(Object::toString)
                .filter(DiffHelper::isNotEmpty)
                .ifPresent(ddl -> writeFileData(ErmCmpDDLGenerator.this.outFile(modifyTableSqlFileName), ddl));

        Optional.ofNullable(currentOutDDL)
                .map(BaseOutDDL::getModifyColumnSqlFix)
                .map(Object::toString)
                .filter(DiffHelper::isNotEmpty)
                .ifPresent(ddl -> writeFileData(outFile(fixFileName(modifyColumnSqlFileName)), ddl));

        Optional.ofNullable(currentOutDDL)
                .map(BaseOutDDL::getModifyIndexSqlFix)
                .map(Object::toString)
                .filter(DiffHelper::isNotEmpty)
                .ifPresent(ddl -> writeFileData(outFile(fixFileName(modifyIndexSqlFileName)), ddl));

        Optional.ofNullable(currentOutDDL)
                .map(BaseOutDDL::getModifyTableSqlFix)
                .map(Object::toString)
                .filter(DiffHelper::isNotEmpty)
                .ifPresent(ddl -> writeFileData(outFile(fixFileName(modifyTableSqlFileName)), ddl));
    }

    private File outFile(String modifyTableSqlFileName) {
        return new File(ermDiffEnv.getOutFilePath() + File.separator + modifyTableSqlFileName);
    }

    private String fixFileName(String sqlFileName) {
        String result;
        int i = sqlFileName.indexOf('.');
        if (i > -1) {
            String filename = sqlFileName.substring(0, i);
            String ext = sqlFileName.substring(i);
            result = filename + "_fix" + ext;
        } else {
            result = sqlFileName + "_fix.sql";
        }
        return result;
    }


    public void setModifyColumnSqlFileName(String modifyColumnSqlFileName) {
        this.modifyColumnSqlFileName = modifyColumnSqlFileName;
    }

    public void setModifyIndexSqlFileName(String modifyIndexSqlFileName) {
        this.modifyIndexSqlFileName = modifyIndexSqlFileName;
    }

    public void setModifyTableSqlFileName(String modifyTableSqlFileName) {
        this.modifyTableSqlFileName = modifyTableSqlFileName;
    }
}
