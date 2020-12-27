package com.github.javahello.erm.generator.core.codegen.ddl;

import com.github.javahello.erm.generator.core.model.db.Table;

/**
 * 输出 ddl
 */
public interface ISqlTableNew extends ICovDDL, IFixDDL {

    ICovDDL newTable(Table table);
}
