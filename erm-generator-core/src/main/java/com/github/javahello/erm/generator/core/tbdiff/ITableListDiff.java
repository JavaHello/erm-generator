package com.github.javahello.erm.generator.core.tbdiff;

import com.github.javahello.erm.generator.core.model.db.Index;
import com.github.javahello.erm.generator.core.model.db.Table;
import com.github.javahello.erm.generator.core.model.diff.DiffTable;

import java.util.List;
import java.util.Optional;

/**
 * @author kaiv2
 */
public interface ITableListDiff extends IDiff<List<Table>, List<DiffTable>> {

    default String diffId(Table table) {
        return table.getTableName();
    }
}
