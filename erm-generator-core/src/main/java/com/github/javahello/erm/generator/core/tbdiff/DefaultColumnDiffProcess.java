package com.github.javahello.erm.generator.core.tbdiff;

import com.github.javahello.erm.generator.core.model.db.Column;
import com.github.javahello.erm.generator.core.model.diff.DiffColumn;

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
        boolean diff = doDiff(d1, d2);
        DiffColumn diffColumn = null;
        if (diff) {
            diffColumn = new DiffColumn();
            diffColumn.setNewColumnName(d1.getColumnName());
            diffColumn.setNewColumnComment(d1.getColumnComment());
            diffColumn.setNewColumn(t1);

            diffColumn.setOldColumnName(d2.getColumnName());
            diffColumn.setOldColumnComment(d2.getColumnComment());
            diffColumn.setOldColumn(t2);
        }
        return Optional.ofNullable(diffColumn);
    }

    protected boolean doDiff(Column d1, Column d2) {
        boolean diff = !Objects.equals(d1.getColumnName(), d2.getColumnName());
        if (!Objects.equals(d1.getDefaultValue(), d2.getDefaultValue())) {
            diff = true;
        }
        if (!Objects.equals(d1.getLength(), d2.getLength())) {
            diff = true;
        }
        if (!Objects.equals(d1.getDecimal(), d2.getDecimal())) {
            diff = true;
        }
        if (!Objects.equals(d1.isAutoIncrement(), d2.isAutoIncrement())) {
            diff = true;
        }
        if (!Objects.equals(d1.isUnsigned(), d2.isUnsigned())) {
            diff = true;
        }
        if (!Objects.equals(d1.isNotNull(), d2.isNotNull())) {
            diff = true;
        }
        return diff;
    }
}
