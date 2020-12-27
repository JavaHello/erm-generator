package com.github.javahello.erm.generator.core.codegen.ddl.mysql;

import com.github.javahello.erm.generator.core.codegen.ddl.mysql.impl.AbstractMysqlCovDDL;
import com.github.javahello.erm.generator.core.model.db.Column;

import java.util.Optional;


/**
 * @author kaiv2
 */
public abstract class AbstractMysqlColumnGen<FixDDL extends IMysqlCovDDL> extends AbstractMysqlCovDDL<FixDDL> {

    protected Column newColumn;

    protected String tbName;

    public AbstractMysqlColumnGen() {
    }

    public AbstractMysqlColumnGen(FixDDL fixDdl) {
        super(fixDdl);
    }


    @Override
    public String covDDL() {
        return Optional.ofNullable(newColumn).map(column -> "ALTER TABLE " + tbName + " " + optColumn() + " " + MySqlDDLHelper.columnGe(column) + ";").orElse("");
    }

    protected abstract String optColumn();


    public void setNewColumn(Column newColumn) {
        this.newColumn = newColumn;
    }

    public void setTbName(String tbName) {
        this.tbName = tbName;
    }
}
