package com.github.javahello.erm.generator.core.tbdiff;

import com.github.javahello.erm.generator.core.model.db.Column;
import com.github.javahello.erm.generator.core.model.diff.DiffColumn;
import com.github.javahello.erm.generator.core.util.MapHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DefaultColumnListDiffProcess implements IColumnListDiff {

    private IColumnDiff columnDiff;

    public DefaultColumnListDiffProcess(IColumnDiff columnDiff) {
        this.columnDiff = columnDiff;
    }

    @Override
    public Optional<List<DiffColumn>> diff(List<Column> t1, List<Column> t2) {
        if (t1 == t2) {
            return Optional.empty();
        }
        List<Column> d1 = Optional.ofNullable(t1).orElseGet(ArrayList::new);
        List<Column> d2 = Optional.ofNullable(t2).orElseGet(ArrayList::new);
        Map<String, Column> columnMap2 = MapHelper.uniqueGroup(d2, Column::getColumnName);

        List<DiffColumn> diffColumnList = new ArrayList<>();
        for (Column column : d1) {
            Column column2 = columnMap2.remove(column.getColumnName());
            Optional<DiffColumn> diffColumnOpt = columnDiff.diff(column, column2);
            diffColumnOpt.ifPresent(diffColumnList::add);
        }
        for (Column column2 : columnMap2.values()) {
            Optional<DiffColumn> diffColumnOpt = columnDiff.diff(null, column2);
            diffColumnOpt.ifPresent(diffColumnList::add);
        }
        return Optional.of(diffColumnList);
    }
}
