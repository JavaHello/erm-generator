package com.github.javahello.erm.generator.core.codegen.ddl.mysql;

import com.github.javahello.erm.generator.core.codegen.ddl.DbType;
import com.github.javahello.erm.generator.core.codegen.ddl.ICovDDL;

public interface IMysqlCovDDL extends ICovDDL {
    @Override
    default DbType dbType() {
        return DbType.MYSQL;
    }
}
