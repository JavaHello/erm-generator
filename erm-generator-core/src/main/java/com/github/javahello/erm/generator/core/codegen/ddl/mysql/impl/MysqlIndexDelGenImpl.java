package com.github.javahello.erm.generator.core.codegen.ddl.mysql.impl;


import com.github.javahello.erm.generator.core.codegen.ddl.ICovDDL;
import com.github.javahello.erm.generator.core.codegen.ddl.ISqlIndexDel;
import com.github.javahello.erm.generator.core.model.db.Index;

import java.util.Optional;

/**
 * @author kaiv2
 */
public class MysqlIndexDelGenImpl extends AbstractMysqlCovDDL<MysqlIndexNewGenImpl> implements ISqlIndexDel {

    protected Index index;
    protected String tbName;


    @Override
    public String covDDL() {
        if (index == null) {
            return "";
        }
        String out = "ALTER TABLE " + tbName + " DROP INDEX "
                + index.getIndexName() + ";";
        this.index = null;
        this.tbName = null;
        return out;
    }

    @Override
    public ICovDDL delIdx(String tbName, Index idx) {
        this.index = idx;
        this.tbName = tbName;
        Optional.ofNullable(fixDdl).ifPresent(f -> f.newIdx(tbName, idx));
        return this;
    }
}
