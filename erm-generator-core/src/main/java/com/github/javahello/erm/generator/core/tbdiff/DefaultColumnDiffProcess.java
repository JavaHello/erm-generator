package com.github.javahello.erm.generator.core.tbdiff;

import com.github.javahello.erm.generator.core.model.db.Column;
import com.github.javahello.erm.generator.core.model.diff.DiffColumn;
import com.github.javahello.erm.generator.core.util.DiffHelper;

import java.util.Objects;
import java.util.Optional;

public class DefaultColumnDiffProcess implements IColumnDiff {


    @Override
    public Optional<DiffColumn> diff(Column t1, Column t2) {
        if (t1 == t2) {
            return Optional.empty();
        }
        Column d1 = Optional.ofNullable(t1).orElseGet(Column::new);
        Column d2 = Optional.ofNullable(t2).orElseGet(Column::new);
        boolean diff = false;
        if (!Objects.equals(d1.getColumnName(), d2.getColumnName())) {
            diff = true;
        }
        if (!Objects.equals(d1.getDefaultValue(), d2.getDefaultValue())) {
            diff = true;
        }
        DiffColumn diffColumn = null;
        if (diff) {
            diffColumn = new DiffColumn();
            diffColumn.setColumnName(DiffHelper.or(d1.getColumnName(), d2.getColumnName()));
            diffColumn.setColumnComment(DiffHelper.or(d1.getColumnComment(), d2.getColumnComment()));
            diffColumn.setNewColumn(t1);
            diffColumn.setOldColumn(t2);
        }
        return Optional.ofNullable(diffColumn);
    }
}
