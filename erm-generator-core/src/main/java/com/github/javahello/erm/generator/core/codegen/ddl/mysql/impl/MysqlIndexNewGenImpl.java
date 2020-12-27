package com.github.javahello.erm.generator.core.codegen.ddl.mysql.impl;


import com.github.javahello.erm.generator.core.codegen.ddl.ICovDDL;
import com.github.javahello.erm.generator.core.codegen.ddl.ISqlIndexNew;
import com.github.javahello.erm.generator.core.codegen.ddl.mysql.MySqlDDLHelper;
import com.github.javahello.erm.generator.core.model.db.Index;
import com.github.javahello.erm.generator.core.util.DiffHelper;

import java.util.Optional;

/**
 * @author kaiv2
 */
public class MysqlIndexNewGenImpl extends AbstractMysqlCovDDL<MysqlIndexDelGenImpl> implements ISqlIndexNew {

    protected Index index;
    protected String tbName;


    @Override
    public String covDDL() {
        if (index == null) {
            return "";
        }
        return "ALTER TABLE " + tbName + " ADD " + MySqlDDLHelper.indexGe(index) + ";";
    }

    @Override
    public ICovDDL newIdx(String tbName, Index idx) {
        this.index = idx;
        this.tbName = tbName;
        Optional.ofNullable(fixDdl).ifPresent(f -> f.delIdx(tbName, idx));
        return this;
    }
}
