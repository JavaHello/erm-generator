package com.github.javahello.erm.generator.core.codegen.ddl.mysql.impl;


import com.github.javahello.erm.generator.core.codegen.ddl.ICovDDL;
import com.github.javahello.erm.generator.core.codegen.ddl.ISqlPkDel;
import com.github.javahello.erm.generator.core.codegen.ddl.mysql.IMysqlCovDDL;

/**
 * @author kaiv2
 */
public class MysqlPrimaryKeyDelGenImpl implements IMysqlCovDDL, ISqlPkDel {

    protected String tbName;

    @Override
    public String covDDL() {
        return "ALTER TABLE " + tbName + " DROP PRIMARY KEY;";
    }


    @Override
    public ICovDDL delPk(String tbName) {
        this.tbName = tbName;
        return this;
    }
}
