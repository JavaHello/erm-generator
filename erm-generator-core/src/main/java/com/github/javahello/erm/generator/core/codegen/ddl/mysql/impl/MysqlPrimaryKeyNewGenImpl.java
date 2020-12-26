package com.github.javahello.erm.generator.core.codegen.ddl.mysql.impl;


import com.github.javahello.erm.generator.core.codegen.ddl.ICovDDL;
import com.github.javahello.erm.generator.core.codegen.ddl.ISqlPkNew;
import com.github.javahello.erm.generator.core.codegen.ddl.mysql.IMysqlCovDDL;
import com.github.javahello.erm.generator.core.model.db.Column;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author kaiv2
 */
public class MysqlPrimaryKeyNewGenImpl implements IMysqlCovDDL, ISqlPkNew {

    protected List<Column> pks;
    protected String tbName;

    @Override
    public String covDDL() {
        return "ALTER TABLE " + tbName + " ADD PRIMARY KEY ("
                + pks.stream().map(Column::getColumnName)
                .collect(Collectors.joining(", ")) + ");";
    }


    @Override
    public ICovDDL newPk(String tbName, List<Column> pks) {
        this.tbName = tbName;
        this.pks = pks;
        return this;
    }
}
