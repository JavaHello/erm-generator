package com.github.javahello.erm.generator.core.api;

import com.github.javahello.erm.generator.core.codegen.ddl.BaseOutDDL;
import com.github.javahello.erm.generator.core.codegen.ddl.ICovDDL;
import com.github.javahello.erm.generator.core.codegen.ddl.mysql.GenMysqlDDL;
import com.github.javahello.erm.generator.core.internal.TableCache;
import com.github.javahello.erm.generator.core.model.ErmDiffEnv;
import com.github.javahello.erm.generator.core.model.diff.DiffTable;
import com.github.javahello.erm.generator.core.tbdiff.DefaultTableListDiffProcess;
import com.github.javahello.erm.generator.core.tbdiff.ITableListDiff;
import com.github.javahello.erm.generator.core.util.DiffHelper;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 比较差异输出 sql
 */
public class ErmCmpDDLGenerator extends AbstractGenerator {


    protected ITableListDiff tableListDiff = new DefaultTableListDiffProcess();
    protected Map<String, BaseOutDDL> outDDLMap = new LinkedHashMap<>();
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
        GenMysqlDDL genMysqlDDL = new GenMysqlDDL(newCache, diffAll);
        outDDLMap.put(genMysqlDDL.dbType().getCode(), genMysqlDDL);
        Optional.ofNullable(doInitDiffProcess(newCache, diffAll))
                .ifPresent(outList -> outList.forEach(out -> outDDLMap.put(out.dbType().getCode(), out)));
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
                sql.getBytes(StandardCharsets.UTF_8)));

        ICovDDL fix = currentOutDDL.fix();

        Optional.ofNullable(fix).map(ICovDDL::covDDL).filter(DiffHelper::isNotEmpty).ifPresent(sql -> writeFileData(outFile(fixFileName(allSqlFileName)),
                sql.getBytes(StandardCharsets.UTF_8)));

        Optional.ofNullable(currentOutDDL)
                .map(BaseOutDDL::getModifyColumnSql)
                .map(Object::toString)
                .filter(DiffHelper::isNotEmpty)
                .ifPresent(ddl -> writeFileData(outFile(modifyColumnSqlFileName)
                        , ddl.getBytes(StandardCharsets.UTF_8)));

        Optional.ofNullable(currentOutDDL)
                .map(BaseOutDDL::getModifyIndexSql)
                .map(Object::toString)
                .filter(DiffHelper::isNotEmpty)
                .ifPresent(ddl -> writeFileData(outFile(modifyIndexSqlFileName)
                        , ddl.getBytes(StandardCharsets.UTF_8)));

        Optional.ofNullable(currentOutDDL)
                .map(BaseOutDDL::getModifyTableSql)
                .map(Object::toString)
                .filter(DiffHelper::isNotEmpty)
                .ifPresent(ddl -> writeFileData(ErmCmpDDLGenerator.this.outFile(modifyTableSqlFileName)
                        , ddl.getBytes(StandardCharsets.UTF_8)));

        Optional.ofNullable(currentOutDDL)
                .map(BaseOutDDL::getModifyColumnSqlFix)
                .map(Object::toString)
                .filter(DiffHelper::isNotEmpty)
                .ifPresent(ddl -> writeFileData(outFile(fixFileName(modifyColumnSqlFileName))
                        , ddl.getBytes(StandardCharsets.UTF_8)));

        Optional.ofNullable(currentOutDDL)
                .map(BaseOutDDL::getModifyIndexSqlFix)
                .map(Object::toString)
                .filter(DiffHelper::isNotEmpty)
                .ifPresent(ddl -> writeFileData(outFile(fixFileName(modifyIndexSqlFileName))
                        , ddl.getBytes(StandardCharsets.UTF_8)));

        Optional.ofNullable(currentOutDDL)
                .map(BaseOutDDL::getModifyTableSqlFix)
                .map(Object::toString)
                .filter(DiffHelper::isNotEmpty)
                .ifPresent(ddl -> writeFileData(outFile(fixFileName(modifyTableSqlFileName))
                        , ddl.getBytes(StandardCharsets.UTF_8)));
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


    protected void writeFileData(File sqlFile, byte[] sqlBytes) {
        try {
            if (sqlFile.exists() || sqlFile.createNewFile()) {
                Files.write(sqlFile.toPath(), sqlBytes);
            } else {
                log.error(sqlFile.getAbsolutePath() + ", 文件创建失败");
            }
        } catch (IOException e) {
            log.error(sqlFile.getAbsolutePath() + "写入SQL到文件失败", e);
        }
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
