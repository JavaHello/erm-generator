package com.github.javahello.erm.generator.core.codegen.ddl.mysql;

import com.github.javahello.erm.generator.core.model.db.Column;


/**
 * @author kaiv2
 */
public abstract class AbstractMysqlColumnGen implements IMysqlCovDDL {

    protected Column newColumn;

    protected String tbName;


    @Override
    public String covDDL() {
        return "ALTER TABLE " + tbName + " " + optColumn() + " " + MySqlDDLHelper.columnGe(newColumn) + ";";
    }

    protected abstract String optColumn();


    public void setNewColumn(Column newColumn) {
        this.newColumn = newColumn;
    }

    public void setTbName(String tbName) {
        this.tbName = tbName;
    }
}
