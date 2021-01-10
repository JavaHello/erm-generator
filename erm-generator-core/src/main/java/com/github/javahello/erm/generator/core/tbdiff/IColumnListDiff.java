package com.github.javahello.erm.generator.core.tbdiff;

import com.github.javahello.erm.generator.core.model.db.Column;
import com.github.javahello.erm.generator.core.model.diff.DiffColumn;

import java.util.List;
import java.util.Optional;

/**
 * @author kaiv2
 */
public interface IColumnListDiff extends IDiff<List<Column>, List<DiffColumn>> {

    default String diffId(Column column) {
        return Optional.ofNullable(column.getId()).orElseGet(column::getColumnName);
    }
}
