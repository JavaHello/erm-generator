package com.github.javahello.erm.generator.core.codegen.ddl.mysql.impl;

import com.github.javahello.erm.generator.core.codegen.ddl.ICovDDL;
import com.github.javahello.erm.generator.core.codegen.ddl.ISqlColumnModify;
import com.github.javahello.erm.generator.core.codegen.ddl.mysql.AbstractMysqlColumnGen;
import com.github.javahello.erm.generator.core.model.db.Column;


/**
 * @author kaiv2
 */
public class MysqlColumnModifyGenImpl extends AbstractMysqlColumnGen implements ISqlColumnModify {

    protected Column oldC;

    @Override
    protected String optColumn() {
        return "CHANGE COLUMN " + String.join(" ", oldC.getColumnName(), newColumn.getColumnName());
    }


    @Override
    public ICovDDL modifyCol(String tbName, Column newC, Column oldC) {
        setTbName(tbName);
        setNewColumn(newC);
        this.oldC = oldC;
        return this;
    }
}
