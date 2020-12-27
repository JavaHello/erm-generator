package com.github.javahello.erm.generator.core.codegen.ddl.mysql;

import com.github.javahello.erm.generator.core.codegen.ddl.DbType;
import com.github.javahello.erm.generator.core.codegen.ddl.ICovDDL;
import com.github.javahello.erm.generator.core.codegen.ddl.IFixDDL;

public interface IMysqlCovDDL extends ICovDDL, IFixDDL {
    @Override
    default DbType dbType() {
        return DbType.MYSQL;
    }
}
