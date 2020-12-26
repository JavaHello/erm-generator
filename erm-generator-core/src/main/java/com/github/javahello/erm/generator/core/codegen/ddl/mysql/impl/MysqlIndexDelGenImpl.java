package com.github.javahello.erm.generator.core.codegen.ddl.mysql.impl;


import com.github.javahello.erm.generator.core.codegen.ddl.ICovDDL;
import com.github.javahello.erm.generator.core.codegen.ddl.ISqlIndexDel;
import com.github.javahello.erm.generator.core.codegen.ddl.mysql.IMysqlCovDDL;
import com.github.javahello.erm.generator.core.model.db.Index;

/**
 * @author kaiv2
 */
public class MysqlIndexDelGenImpl implements IMysqlCovDDL, ISqlIndexDel {

    protected Index index;
    protected String tbName;

    @Override
    public String covDDL() {
        return "ALTER TABLE " + tbName + " DROP INDEX "
                + index.getIndexName() + ";";
    }

    @Override
    public ICovDDL delIdx(String tbName, Index idx) {
        this.index = idx;
        this.tbName = tbName;
        return this;
    }
}
