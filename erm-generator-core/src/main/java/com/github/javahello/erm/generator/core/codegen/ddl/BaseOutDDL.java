package com.github.javahello.erm.generator.core.codegen.ddl;

import com.github.javahello.erm.generator.core.internal.TableCache;
import com.github.javahello.erm.generator.core.model.db.Column;
import com.github.javahello.erm.generator.core.model.db.Table;
import com.github.javahello.erm.generator.core.model.diff.DiffColumn;
import com.github.javahello.erm.generator.core.model.diff.DiffEnum;
import com.github.javahello.erm.generator.core.model.diff.DiffTable;

import java.util.List;
import java.util.Optional;
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
            if (DiffEnum.A == diffTable.getDiffEnum()) {
                Table table = newTableCache.getTable(tableName)
                        .orElseThrow(() -> new IllegalArgumentException("CREATE TABLE OUT DDL 没有找到表:" + tableName));
                addCreateTable(newTable(table).covDDL());
            } else if (DiffEnum.D == diffTable.getDiffEnum()) {
                Table table = newTableCache.getTable(tableName)
                        .orElseThrow(() -> new IllegalArgumentException("DROP TABLE OUT DDL 没有找到表:" + tableName));
                addCreateTable(delTable(table).covDDL());
            } else {
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
                Optional.ofNullable(diffTable.getDiffPks()).ifPresent(pks -> {
                    addAlterIndex(delPk(tableName, pks.stream().map(DiffColumn::getOldColumn).collect(Collectors.toList())).covDDL());
                    addAlterIndex(newPk(tableName, pks.stream().map(DiffColumn::getNewColumn).collect(Collectors.toList())).covDDL());
                });

                Optional.ofNullable(diffTable.getDiffIndexs()).ifPresent(diffIndexs -> {
                    diffIndexs.forEach(diffIndex -> {
                        Optional.ofNullable(diffIndex.getOldIndex()).ifPresent(idx -> {
                            addAlterIndex(delIdx(tableName, idx).covDDL());
                        });
                        Optional.ofNullable(diffIndex.getNewIndex()).ifPresent(idx -> {
                            addAlterIndex(newIdx(tableName, idx).covDDL());
                        });
                    });
                });
            }
        }
        return createTableSql.append(alterColumn).append(alterIndex).toString();
    }
}
