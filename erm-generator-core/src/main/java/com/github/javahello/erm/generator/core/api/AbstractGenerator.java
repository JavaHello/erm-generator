package com.github.javahello.erm.generator.core.api;

import com.github.javahello.erm.generator.core.internal.ErmRead;
import com.github.javahello.erm.generator.core.internal.TableCache;
import com.github.javahello.erm.generator.core.model.ErmDiffEnv;
import org.mybatis.generator.logging.Log;
import org.mybatis.generator.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public abstract class AbstractGenerator {

    protected Log log = LogFactory.getLog(getClass());

    protected ErmDiffEnv ermDiffEnv;

    protected TableCache newCache;
    protected TableCache oldCache;


    public AbstractGenerator(ErmDiffEnv ermDiffEnv) {
        Objects.requireNonNull(ermDiffEnv, "ErmDDLEnv 环境变量不能为空");
        this.ermDiffEnv = ermDiffEnv;
    }


    public final void exec() {
        doInitEnv();
        beforeExec();
        try {
            runExec();
            afterExec();
        } catch (Exception e) {
            errorExec(e);
        }
    }


    public void setNewCache(TableCache newCache) {
        this.newCache = newCache;
    }

    public void setOldCache(TableCache oldCache) {
        this.oldCache = oldCache;
    }

    protected void beforeExec() {
    }

    ;

    protected abstract void runExec() throws Exception;

    protected void afterExec() {
    }

    protected void errorExec(Exception exception) {
        throw new RuntimeException(exception);
    }

    protected void doInitEnv() {
        if (newCache == null) {
            newCache = new ErmRead(ermDiffEnv.getNewErmList());
        }
        if (oldCache == null) {
            List<String> oldErmList = ermDiffEnv.getOldErmList();
            oldCache = new ErmRead(Optional.ofNullable(oldErmList).orElseGet(ArrayList::new));
        }
    }

    ;
}
