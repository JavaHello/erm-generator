package com.github.javahello.erm.generator.core.tbdiff;

import com.github.javahello.erm.generator.core.model.db.Column;
import com.github.javahello.erm.generator.core.model.diff.DiffColumn;

import java.util.List;

/**
 * @author kaiv2
 */
public interface IColumnDiff extends IDiff<Column, DiffColumn> {

    @Override
    default String diffId(Column column) {
        return column.getColumnName();
    }
}
