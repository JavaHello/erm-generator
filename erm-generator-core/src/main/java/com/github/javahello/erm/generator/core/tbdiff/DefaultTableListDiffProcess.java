package com.github.javahello.erm.generator.core.tbdiff;

import com.github.javahello.erm.generator.core.model.db.Table;
import com.github.javahello.erm.generator.core.model.diff.DiffTable;
import com.github.javahello.erm.generator.core.util.MapHelper;

import java.util.*;

/**
 * @author kaiv2
 */
public class DefaultTableListDiffProcess implements ITableListDiff {

    private ITableDiff tableDiff = new DefaultTableDiffProcess();

    public void setTableDiff(ITableDiff tableDiff) {
        this.tableDiff = tableDiff;
    }

    @Override
    public Optional<List<DiffTable>> diff(List<Table> t1, List<Table> t2) {
        if (t1 == t2) {
            return Optional.empty();
        }
        List<Table> d1 = Optional.ofNullable(t1).orElseGet(ArrayList::new);
        List<Table> d2 = Optional.ofNullable(t2).orElseGet(ArrayList::new);
        Map<String, Table> tableMap2 = MapHelper.uniqueGroup(d2, Table::getTableName);

        List<DiffTable> diffColumnList = new ArrayList<>();
        for (Table table1 : d1) {
            Table table2 = tableMap2.remove(table1.getTableName());
            Optional<DiffTable> diffColumnOpt = tableDiff.diff(table1, table2);
            diffColumnOpt.ifPresent(diffColumnList::add);
        }
        tableMap2.values().stream().sorted(Comparator.comparing(Table::getTableName))
                .forEach(table2 -> tableDiff.diff(null, table2).ifPresent(diffColumnList::add));
        return Optional.of(diffColumnList);
    }
}
