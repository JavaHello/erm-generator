package com.github.javahello.erm.generator.core.codegen.ddl;

import java.util.ArrayList;
import java.util.List;

import com.github.javahello.erm.generator.core.internal.ErmMetaData;
import com.github.javahello.erm.generator.core.model.db.Column;
import com.github.javahello.erm.generator.core.model.db.Table;
import com.github.javahello.erm.generator.core.model.diff.DiffColumn;
import com.github.javahello.erm.generator.core.model.diff.DiffIndex;
import com.github.javahello.erm.generator.core.model.diff.DiffTable;

public abstract class BaseOutDDL implements ISqlTable, ISqlColumn, ISqlIndex, ISqlPks {

    ErmMetaData ermMetaData;
    List<DiffTable> diffTables;
    List<ICovDDL> covDDLs = new ArrayList<>();

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
                Table table = ermMetaData.getTable(tableName)
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
            for (DiffColumn diffColumn : diffPks) {
                addAlterIndex(delPk(tableName, diffColumn.getOldColumn()).covDDL());
                addAlterIndex(newPk(tableName, diffColumn.getNewColumn()).covDDL());
            }

            List<DiffIndex> diffIndexs = diffTable.getDiffIndexs();
            for (DiffIndex diffIndex : diffIndexs) {
                addAlterIndex(delIdx(tableName, diffIndex.getOldIndex()).covDDL());
                addAlterIndex(newIdx(tableName, diffIndex.getNewIndex()).covDDL());
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append(createTableSql).append("\n");
        sb.append(alterColumn).append("\n");
        sb.append(alterIndex).append("\n");
        return sb.toString();
    }
}
