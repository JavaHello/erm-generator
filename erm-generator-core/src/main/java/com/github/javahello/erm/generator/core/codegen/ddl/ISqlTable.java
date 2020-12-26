package com.github.javahello.erm.generator.core.codegen.ddl;

import java.util.HashMap;
import java.util.Map;

import com.github.javahello.erm.generator.core.model.db.Table;

/**
 * 输出 ddl
 */
public interface ISqlTable extends ICovDDL {
    public static final Map<DbType, ISqlTable> CT_MAP = new HashMap<>();

    ICovDDL tb(Table table);
}
