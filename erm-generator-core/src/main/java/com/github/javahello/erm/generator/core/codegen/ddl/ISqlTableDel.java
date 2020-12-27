package com.github.javahello.erm.generator.core.codegen.ddl;

import com.github.javahello.erm.generator.core.model.db.Table;

/**
 * 输出 ddl
 */
public interface ISqlTableDel extends ICovDDL, IFixDDL {

    ICovDDL delTable(Table table);
}
