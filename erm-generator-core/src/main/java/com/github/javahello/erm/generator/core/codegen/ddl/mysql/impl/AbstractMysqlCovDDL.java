package com.github.javahello.erm.generator.core.codegen.ddl.mysql.impl;

import com.github.javahello.erm.generator.core.codegen.ddl.ICovDDL;
import com.github.javahello.erm.generator.core.codegen.ddl.mysql.IMysqlCovDDL;

/**
 * @author kaiv2
 */
public abstract class AbstractMysqlCovDDL<FixDDL extends ICovDDL> implements IMysqlCovDDL {

    protected FixDDL fixDdl;

    public AbstractMysqlCovDDL() {
    }

    public AbstractMysqlCovDDL(FixDDL fixDdl) {
        this.fixDdl = fixDdl;
    }

    @SuppressWarnings("unchecked")
    public void setFixDdl(ICovDDL fixDdl) {
        this.fixDdl = (FixDDL) fixDdl;
    }

    @Override
    public ICovDDL fix() {
        return fixDdl;
    }
}
