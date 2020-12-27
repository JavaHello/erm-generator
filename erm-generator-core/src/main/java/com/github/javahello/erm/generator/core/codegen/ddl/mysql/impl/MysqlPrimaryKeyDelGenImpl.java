package com.github.javahello.erm.generator.core.codegen.ddl.mysql.impl;


import com.github.javahello.erm.generator.core.codegen.ddl.ICovDDL;
import com.github.javahello.erm.generator.core.codegen.ddl.ISqlPkDel;
import com.github.javahello.erm.generator.core.model.db.Column;
import com.github.javahello.erm.generator.core.util.DiffHelper;

import java.util.List;
import java.util.Optional;

/**
 * @author kaiv2
 */
public class MysqlPrimaryKeyDelGenImpl extends AbstractMysqlCovDDL<MysqlPrimaryKeyNewGenImpl> implements ISqlPkDel {

    protected List<Column> pks;
    protected String tbName;


    @Override
    public String covDDL() {
        if (DiffHelper.isEmpty(pks)) {
            return "";
        }
        String out = "ALTER TABLE " + tbName + " DROP PRIMARY KEY;";
        this.pks = null;
        this.tbName = null;
        return out;
    }


    @Override
    public ICovDDL delPk(String tbName, List<Column> pks) {
        this.tbName = tbName;
        this.pks = pks;
        Optional.ofNullable(fixDdl).ifPresent(f -> f.newPk(tbName, pks));
        return this;
    }
}
