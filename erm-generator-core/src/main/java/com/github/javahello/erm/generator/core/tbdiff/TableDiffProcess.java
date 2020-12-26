package com.github.javahello.erm.generator.core.tbdiff;

import com.github.javahello.erm.generator.core.model.db.Table;
import com.github.javahello.erm.generator.core.model.diff.DiffColumn;
import com.github.javahello.erm.generator.core.model.diff.DiffIndex;
import com.github.javahello.erm.generator.core.model.diff.DiffTable;
import com.github.javahello.erm.generator.core.util.DiffHelper;

import java.util.List;
import java.util.Optional;

/**
 * @author kaiv2
 */
public class TableDiffProcess implements ITableDiff {

    private IColumnListDiff columnListDiff = new DefaultColumnListDiffProcess(new DefaultColumnDiffProcess());
    private IColumnListDiff pksDiff = new DefaultColumnListDiffProcess(new DefaultPrimaryKeyDiffProcess());
    private IIndexListDiff indexListDiff = new DefaultIndexListDiffProcess();

    @Override
    public Optional<DiffTable> diff(Table t1, Table t2) {
        if (t1 == t2) {
            return Optional.empty();
        }
        Table d1 = Optional.ofNullable(t1).orElseGet(Table::new);
        Table d2 = Optional.ofNullable(t2).orElseGet(Table::new);
        DiffTable diffTable = null;
        Optional<List<DiffColumn>> diffColumnList = columnListDiff.diff(d1.getColumns(), d2.getColumns());
        Optional<List<DiffIndex>> diffIndexList = indexListDiff.diff(d1.getIndexs(), d2.getIndexs());
        Optional<List<DiffColumn>> diffPks = pksDiff.diff(d1.getPrimaryKeys(), d2.getPrimaryKeys());

        if (diffColumnList.isPresent() || diffIndexList.isPresent() || diffPks.isPresent()) {
            diffTable = new DiffTable();
            diffTable.setTableName(DiffHelper.or(d1.getTableName(), d2.getTableName()));
            diffTable.setNewTb(t2 == null || DiffHelper.isEmpty(t2.getColumns()));
            diffColumnList.ifPresent(diffTable::setDiffColumns);
            diffIndexList.ifPresent(diffTable::setDiffIndexs);
            diffPks.ifPresent(diffTable::setDiffPks);
        }
        return Optional.ofNullable(diffTable);
    }

}
