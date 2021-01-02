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

    private final StringBuilder modifyTableSql = new StringBuilder();
    private final StringBuilder modifyIndexSql = new StringBuilder();
    private final StringBuilder modifyColumnSql = new StringBuilder();

    private StringBuilder addModifyTable(String sql) {
        modifyTableSql.append(sql).append("\n");
        return modifyTableSql;
    }

    private StringBuilder addModifyIndex(String sql) {
        modifyIndexSql.append(sql).append("\n");
        return modifyIndexSql;
    }

    private StringBuilder addModifyColumn(String sql) {
        modifyColumnSql.append(sql).append("\n");
        return modifyColumnSql;
    }

    @Override
    public String covDDL() {
        doInitFix();
        for (DiffTable diffTable : diffTables) {
            String tableName = diffTable.getTableName();
            if (DiffEnum.A == diffTable.getDiffEnum()) {
                Table table = newTableCache.getTable(tableName)
                        .orElseThrow(() -> new IllegalArgumentException("CREATE TABLE OUT DDL 没有找到表:" + tableName));
                addModifyTable(newTable(table).covDDL());
            } else if (DiffEnum.D == diffTable.getDiffEnum()) {
                Table table = newTableCache.getTable(tableName)
                        .orElseThrow(() -> new IllegalArgumentException("DROP TABLE OUT DDL 没有找到表:" + tableName));
                addModifyTable(delTable(table).covDDL());
            } else {
                List<DiffColumn> diffColumns = diffTable.getDiffColumns();
                for (DiffColumn diffColumn : diffColumns) {
                    Column newColumn = diffColumn.getNewColumn();
                    Column oldColumn = diffColumn.getOldColumn();
                    if (newColumn == null) {
                        addModifyColumn(delCol(tableName, oldColumn).covDDL());
                    } else if (oldColumn == null) {
                        addModifyColumn(newCol(tableName, newColumn).covDDL());
                    } else {
                        addModifyColumn(modifyCol(tableName, newColumn, oldColumn).covDDL());
                    }
                }
                Optional.ofNullable(diffTable.getDiffPks()).ifPresent(pks -> {
                    addModifyIndex(delPk(tableName, pks.stream().map(DiffColumn::getOldColumn).collect(Collectors.toList())).covDDL());
                    addModifyIndex(newPk(tableName, pks.stream().map(DiffColumn::getNewColumn).collect(Collectors.toList())).covDDL());
                });

                Optional.ofNullable(diffTable.getDiffIndexs()).ifPresent(diffIndexs -> {
                    diffIndexs.forEach(diffIndex -> {
                        Optional.ofNullable(diffIndex.getOldIndex()).ifPresent(idx -> {
                            addModifyIndex(delIdx(tableName, idx).covDDL());
                        });
                        Optional.ofNullable(diffIndex.getNewIndex()).ifPresent(idx -> {
                            addModifyIndex(newIdx(tableName, idx).covDDL());
                        });
                    });
                });
            }
            doFixRec();
        }
        return modifyTableSql + "" + modifyColumnSql + modifyIndexSql;
    }

    protected abstract void doFixRec();

    protected abstract void doInitFix();

    public StringBuilder getModifyColumnSql() {
        return modifyColumnSql;
    }

    public StringBuilder getModifyIndexSql() {
        return modifyIndexSql;
    }

    public StringBuilder getModifyTableSql() {
        return modifyTableSql;
    }
}
