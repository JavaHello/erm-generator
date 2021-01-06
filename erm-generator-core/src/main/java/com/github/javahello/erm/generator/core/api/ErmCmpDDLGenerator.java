package com.github.javahello.erm.generator.core.api;

import com.github.javahello.erm.generator.core.codegen.ddl.BaseOutDDL;
import com.github.javahello.erm.generator.core.codegen.ddl.mysql.GenMysqlDDL;
import com.github.javahello.erm.generator.core.internal.TableCache;
import com.github.javahello.erm.generator.core.model.ErmDiffEnv;
import com.github.javahello.erm.generator.core.model.diff.DiffTable;
import com.github.javahello.erm.generator.core.tbdiff.DefaultTableListDiffProcess;
import com.github.javahello.erm.generator.core.tbdiff.ITableListDiff;

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

    public ErmCmpDDLGenerator(ErmDiffEnv ermDiffEnv) {
        super(ermDiffEnv);
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
}
