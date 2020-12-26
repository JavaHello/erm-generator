package com.github.javahello.erm.generator.core.codegen.ddl.mysql;

import com.github.javahello.erm.generator.core.codegen.ddl.BaseOutDDL;
import com.github.javahello.erm.generator.core.codegen.ddl.DbType;
import com.github.javahello.erm.generator.core.codegen.ddl.ICovDDL;
import com.github.javahello.erm.generator.core.model.db.Column;
import com.github.javahello.erm.generator.core.model.db.Index;
import com.github.javahello.erm.generator.core.model.db.Table;

/**
 * 生成 mysql 格式 DDL
 * 
 * @author kaiv2
 */
public class GenMysqlDDL extends BaseOutDDL {

    @Override
    public ICovDDL tb(Table table) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DbType dbType() {
        return DbType.MYSQL;
    }

    @Override
    public ICovDDL newCol(String tbName, Column col) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ICovDDL delCol(String tbName, Column col) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ICovDDL modifyCol(String tbName, Column newC, Column oldC) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ICovDDL delIdx(String tbName, Index idx) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ICovDDL newIdx(String tbName, Index idx) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ICovDDL delPk(String tbName, Column pk) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ICovDDL newPk(String tbName, Column pk) {
        // TODO Auto-generated method stub
        return null;
    }
}
