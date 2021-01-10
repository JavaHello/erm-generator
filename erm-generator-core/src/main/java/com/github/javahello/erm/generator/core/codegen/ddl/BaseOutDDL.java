package com.github.javahello.erm.generator.core.codegen.ddl;

import com.github.javahello.erm.generator.core.internal.TableCache;
import com.github.javahello.erm.generator.core.model.db.Column;
import com.github.javahello.erm.generator.core.model.db.Table;
import com.github.javahello.erm.generator.core.model.diff.DiffColumn;
import com.github.javahello.erm.generator.core.model.diff.DiffEnum;
import com.github.javahello.erm.generator.core.model.diff.DiffTable;
import com.github.javahello.erm.generator.core.util.DiffHelper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class BaseOutDDL implements ISqlAll {

    TableCache newTableCache;
    TableCache oldTableCache;
    List<DiffTable> diffTables;

    public BaseOutDDL(TableCache newTableCache, List<DiffTable> diffTables) {
        this.newTableCache = newTableCache;
        this.diffTables = diffTables;
    }

    private final StringBuilder modifyTableSql = new StringBuilder();
    private final StringBuilder modifyIndexSql = new StringBuilder();
    private final StringBuilder modifyColumnSql = new StringBuilder();

    private final StringBuilder modifyTableSqlFix = new StringBuilder();
    private final StringBuilder modifyIndexSqlFix = new StringBuilder();
    private final StringBuilder modifyColumnSqlFix = new StringBuilder();

    private StringBuilder addModifyTable(ICovDDL iCovDDL) {
        modifyTableSql.append(iCovDDL.covDDL()).append("\n");
        if (iCovDDL instanceof IFixDDL) {
            addModifyTableFix(((IFixDDL) iCovDDL).fix());
        }
        return modifyTableSql;
    }

    private StringBuilder addModifyIndex(ICovDDL iCovDDL) {
        modifyIndexSql.append(iCovDDL.covDDL()).append("\n");
        if (iCovDDL instanceof IFixDDL) {
            addModifyIndexFix(((IFixDDL) iCovDDL).fix());
        }
        return modifyIndexSql;
    }

    private StringBuilder addModifyColumn(ICovDDL iCovDDL) {
        modifyColumnSql.append(iCovDDL.covDDL()).append("\n");
        if (iCovDDL instanceof IFixDDL) {
            addModifyColumnFix(((IFixDDL) iCovDDL).fix());
        }
        return modifyColumnSql;
    }

    private StringBuilder addModifyTableFix(ICovDDL iCovDDL) {
        modifyTableSqlFix.append(iCovDDL.covDDL()).append("\n");
        return modifyTableSqlFix;
    }

    private StringBuilder addModifyIndexFix(ICovDDL iCovDDL) {
        modifyIndexSqlFix.append(iCovDDL.covDDL()).append("\n");
        return modifyIndexSqlFix;
    }

    private StringBuilder addModifyColumnFix(ICovDDL iCovDDL) {
        modifyColumnSqlFix.append(iCovDDL.covDDL()).append("\n");
        return modifyColumnSqlFix;
    }

    public void setOldTableCache(TableCache oldTableCache) {
        this.oldTableCache = oldTableCache;
    }

    @Override
    public String covDDL() {
        doInitFix();
        for (DiffTable diffTable : diffTables) {
            String tableName = diffTable.getTableName();
            if (DiffEnum.A == diffTable.getDiffEnum()) {
                Table table = newTableCache.getTable(tableName)
                        .orElseThrow(() -> new IllegalArgumentException("CREATE TABLE OUT DDL 没有找到表:" + tableName));
                addModifyTable(newTable(table));
            } else if (DiffEnum.D == diffTable.getDiffEnum()) {
                Table table = oldTableCache.getTable(tableName)
                        .orElseThrow(() -> new IllegalArgumentException("DROP TABLE OUT DDL 没有找到表:" + tableName));
                addModifyTable(delTable(table));
            } else {
                List<DiffColumn> diffColumns = diffTable.getDiffColumns();
                for (DiffColumn diffColumn : diffColumns) {
                    Column newColumn = diffColumn.getNewColumn();
                    Column oldColumn = diffColumn.getOldColumn();
                    if (newColumn == null) {
                        addModifyColumn(delCol(tableName, oldColumn));
                    } else if (oldColumn == null) {
                        addModifyColumn(newCol(tableName, newColumn));
                    } else {
                        addModifyColumn(modifyCol(tableName, newColumn, oldColumn));
                    }
                }
                Optional.ofNullable(diffTable.getDiffPks()).filter(DiffHelper::isNotEmpty).ifPresent(pks -> {
                    addModifyIndex(delPk(tableName, pks.stream().map(DiffColumn::getOldColumn).collect(Collectors.toList())));
                    addModifyIndex(newPk(tableName, pks.stream().map(DiffColumn::getNewColumn).collect(Collectors.toList())));
                });

                Optional.ofNullable(diffTable.getDiffIndexs()).filter(DiffHelper::isNotEmpty).ifPresent(diffIndexs -> {
                    diffIndexs.forEach(diffIndex -> {
                        Optional.ofNullable(diffIndex.getOldIndex()).ifPresent(idx -> {
                            addModifyIndex(delIdx(tableName, idx));
                        });
                        Optional.ofNullable(diffIndex.getNewIndex()).ifPresent(idx -> {
                            addModifyIndex(newIdx(tableName, idx));
                        });
                    });
                });
            }
        }
        return modifyTableSql + "" + modifyColumnSql + modifyIndexSql;
    }

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

    public StringBuilder getModifyTableSqlFix() {
        return modifyTableSqlFix;
    }

    public StringBuilder getModifyIndexSqlFix() {
        return modifyIndexSqlFix;
    }

    public StringBuilder getModifyColumnSqlFix() {
        return modifyColumnSqlFix;
    }
}
