package com.github.javahello.erm.generator.core.codegen.ddl.mysql.impl;

import com.github.javahello.erm.generator.core.codegen.ddl.ICovDDL;
import com.github.javahello.erm.generator.core.codegen.ddl.ISqlColumnNew;
import com.github.javahello.erm.generator.core.codegen.ddl.mysql.AbstractMysqlColumnGen;
import com.github.javahello.erm.generator.core.model.db.Column;


/**
 * @author kaiv2
 */
public class MysqlColumnNewGenImpl extends AbstractMysqlColumnGen implements ISqlColumnNew {


    @Override
    protected String optColumn() {
        return "ADD COLUMN " + newColumn.getColumnName();
    }


    @Override
    public ICovDDL newCol(String tbName, Column col) {
        setTbName(tbName);
        setNewColumn(col);
        return this;
    }
}
