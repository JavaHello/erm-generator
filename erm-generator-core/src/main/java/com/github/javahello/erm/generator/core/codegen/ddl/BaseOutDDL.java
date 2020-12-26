package com.github.javahello.erm.generator.core.codegen.ddl;

import com.github.javahello.erm.generator.core.internal.TableCache;
import com.github.javahello.erm.generator.core.model.db.Column;
import com.github.javahello.erm.generator.core.model.db.Table;
import com.github.javahello.erm.generator.core.model.diff.DiffColumn;
import com.github.javahello.erm.generator.core.model.diff.DiffIndex;
import com.github.javahello.erm.generator.core.model.diff.DiffTable;

import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseOutDDL implements ISqlAll {

    TableCache newTableCache;
    List<DiffTable> diffTables;

    public BaseOutDDL(TableCache newTableCache, List<DiffTable> diffTables) {
        this.newTableCache = newTableCache;
        this.diffTables = diffTables;
    }

    private StringBuilder createTableSql = new StringBuilder();
    private StringBuilder alterIndex = new StringBuilder();
    private StringBuilder alterColumn = new StringBuilder();

    private StringBuilder addCreateTable(String sql) {
        createTableSql.append(sql).append("\n");
        return createTableSql;
    }

    private StringBuilder addAlterIndex(String sql) {
        alterIndex.append(sql).append("\n");
        return alterIndex;
    }

    private StringBuilder addAlterColumn(String sql) {
        alterColumn.append(sql).append("\n");
        return alterColumn;
    }

    @Override
    public String covDDL() {
        for (DiffTable diffTable : diffTables) {
            String tableName = diffTable.getTableName();
            if (diffTable.isNewTb()) {
                Table table = newTableCache.getTable(tableName)
                        .orElseThrow(() -> new IllegalArgumentException("OUT DDL 没有找到表:" + tableName));
                addCreateTable(tb(table).covDDL());
                continue;
            }
            List<DiffColumn> diffColumns = diffTable.getDiffColumns();
            for (DiffColumn diffColumn : diffColumns) {
                Column newColumn = diffColumn.getNewColumn();
                Column oldColumn = diffColumn.getOldColumn();
                if (newColumn == null) {
                    addAlterColumn(delCol(tableName, oldColumn).covDDL());
                } else if (oldColumn == null) {
                    addAlterColumn(newCol(tableName, newColumn).covDDL());
                } else {
                    addAlterColumn(modifyCol(tableName, newColumn, oldColumn).covDDL());
                }
            }
            List<DiffColumn> diffPks = diffTable.getDiffPks();
            addAlterIndex(delPk(tableName).covDDL());
            addAlterIndex(newPk(tableName, diffPks.stream().map(DiffColumn::getNewColumn).collect(Collectors.toList())).covDDL());

            List<DiffIndex> diffIndexs = diffTable.getDiffIndexs();
            for (DiffIndex diffIndex : diffIndexs) {
                addAlterIndex(delIdx(tableName, diffIndex.getOldIndex()).covDDL());
                addAlterIndex(newIdx(tableName, diffIndex.getNewIndex()).covDDL());
            }
        }
        return createTableSql + "\n" +
                alterColumn + "\n" +
                alterIndex + "\n";
    }
}
