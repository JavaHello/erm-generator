package com.github.javahello.erm.generator.core.internal;

import com.github.javahello.erm.generator.core.codegen.ddl.DbType;
import com.github.javahello.erm.generator.core.model.db.Table;

import java.util.List;
import java.util.Optional;

public interface TableCache {
    DbType dbType();
    Optional<Table> getTable(String table);
    List<Table> getTables();
}
