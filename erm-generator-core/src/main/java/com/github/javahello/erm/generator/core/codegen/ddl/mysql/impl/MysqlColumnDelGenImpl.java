package com.github.javahello.erm.generator.core.codegen.ddl.mysql.impl;

import com.github.javahello.erm.generator.core.codegen.ddl.ICovDDL;
import com.github.javahello.erm.generator.core.codegen.ddl.ISqlColumnDel;
import com.github.javahello.erm.generator.core.codegen.ddl.mysql.AbstractMysqlColumnGen;
import com.github.javahello.erm.generator.core.model.db.Column;

import java.util.Optional;


/**
 * @author kaiv2
 */
public class MysqlColumnDelGenImpl extends AbstractMysqlColumnGen<MysqlColumnNewGenImpl> implements ISqlColumnDel {


    @Override
    protected String optColumn() {
        return "DROP COLUMN " + newColumn.getColumnName();
    }


    @Override
    public ICovDDL delCol(String tbName, Column col) {
        setTbName(tbName);
        setNewColumn(col);
        Optional.ofNullable(fixDdl).ifPresent(f -> f.newCol(tbName, col));
        return this;
    }
}
