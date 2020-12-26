package com.github.javahello.erm.generator.core.codegen.ddl.mysql.impl;


import com.github.javahello.erm.generator.core.codegen.ddl.ICovDDL;
import com.github.javahello.erm.generator.core.codegen.ddl.ISqlIndexNew;
import com.github.javahello.erm.generator.core.codegen.ddl.mysql.IMysqlCovDDL;
import com.github.javahello.erm.generator.core.codegen.ddl.mysql.MySqlDDLHelper;
import com.github.javahello.erm.generator.core.model.db.Index;

/**
 * @author kaiv2
 */
public class MysqlIndexNewGenImpl implements IMysqlCovDDL, ISqlIndexNew {

    protected Index index;
    protected String tbName;

    @Override
    public String covDDL() {
        return "ALTER TABLE " + tbName + " ADD " + MySqlDDLHelper.indexGe(index) + ";";
    }

    @Override
    public ICovDDL newIdx(String tbName, Index idx) {
        this.index = idx;
        this.tbName = tbName;
        return this;
    }
}
