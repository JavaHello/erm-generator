package com.github.javahello.erm.generator.core.api;

import com.github.javahello.erm.generator.core.codegen.ddl.BaseOutDDL;
import com.github.javahello.erm.generator.core.codegen.ddl.mysql.GenMysqlDDL;
import com.github.javahello.erm.generator.core.internal.ErmRead;
import com.github.javahello.erm.generator.core.internal.TableCache;
import com.github.javahello.erm.generator.core.model.ErmDDLEnv;
import com.github.javahello.erm.generator.core.model.diff.DiffTable;
import com.github.javahello.erm.generator.core.tbdiff.DefaultTableListDiffProcess;
import com.github.javahello.erm.generator.core.tbdiff.ITableListDiff;

import java.util.*;

/**
 * 比较差异输出 sql
 */
public class ErmCmpDDLGenerator {

    protected ErmDDLEnv ermDDLEnv;

    protected TableCache newCache;
    protected TableCache oldCache;

    protected ITableListDiff tableListDiff = new DefaultTableListDiffProcess();
    protected Map<String, BaseOutDDL> outDDLMap = new LinkedHashMap<>();
    protected String allSql;
    protected BaseOutDDL currentOutDDL;

    public ErmCmpDDLGenerator(ErmDDLEnv ermDDLEnv) {
        Objects.requireNonNull(ermDDLEnv, "ErmDDLEnv 环境变量不能为空");
        this.ermDDLEnv = ermDDLEnv;
    }


    public final void exec() {
        doInitEnv();
        beforeExec();
        runExec();
        afterExec();
    }

    protected void afterExec() {
    }

    protected void runExec() {
        Optional<List<DiffTable>> diffAllOpt = tableListDiff.diff(newCache.getTables(), oldCache.getTables());
        diffAllOpt.ifPresent(diffAll -> {
            this.initDiffProcess(diffAll);
            currentOutDDL = outDDLMap.get(ermDDLEnv.getDbType());
            if (currentOutDDL == null) {
                throw new IllegalArgumentException("数据库类型不支持: " + ermDDLEnv.getDbType());
            }
            allSql = currentOutDDL.covDDL();
        });
    }

    protected void initDiffProcess(List<DiffTable> diffAll) {
        GenMysqlDDL genMysqlDDL = new GenMysqlDDL(newCache, diffAll);
        outDDLMap.put(genMysqlDDL.dbType().name(), genMysqlDDL);
        Optional.ofNullable(doInitDiffProcess(newCache, diffAll))
                .ifPresent(outList -> outList.forEach(out -> outDDLMap.put(out.dbType().name(), out)));
    }

    protected List<BaseOutDDL> doInitDiffProcess(TableCache newCache, List<DiffTable> diffAll) {
        return null;
    }

    protected void beforeExec() {
    }

    protected void doInitEnv() {
        if (newCache == null) {
            newCache = new ErmRead(ermDDLEnv.getNewErmList());
        }
        if (oldCache == null) {
            List<String> oldErmList = ermDDLEnv.getOldErmList();
            oldCache = new ErmRead(Optional.ofNullable(oldErmList).orElseGet(ArrayList::new));
        }
    }
}
