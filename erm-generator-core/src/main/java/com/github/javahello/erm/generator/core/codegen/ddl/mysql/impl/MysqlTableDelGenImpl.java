package com.github.javahello.erm.generator.core.codegen.ddl.mysql.impl;

import com.github.javahello.erm.generator.core.codegen.ddl.ICovDDL;
import com.github.javahello.erm.generator.core.codegen.ddl.ISqlTableDel;
import com.github.javahello.erm.generator.core.model.db.Table;

import java.util.Optional;

/**
 * @author kaiv2
 */
public class MysqlTableDelGenImpl extends AbstractMysqlCovDDL<MysqlTableNewGenImpl> implements ISqlTableDel {

    private Table table;


    @Override
    public String covDDL() {
        if (table == null) {
            return "";
        }
        String out = "DROP TABLE " + table.getTableName() + ";";
        this.table = null;
        return out;
    }


    @Override
    public ICovDDL delTable(Table table) {
        this.table = table;
        Optional.ofNullable(fixDdl).ifPresent(f -> f.newTable(table));
        return this;
    }
}
