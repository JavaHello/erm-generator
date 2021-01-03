package com.github.javahello.erm.generator.core.model.db;

import java.util.List;


/**
 * @author kaiv2
 */
public class Table {
    private String tableName;
    private String tableComment;
    private List<Column> columns;
    private List<Column> primaryKeys;
    private List<Index> indexes;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public List<Column> getPrimaryKeys() {
        return primaryKeys;
    }

    public void setPrimaryKeys(List<Column> primaryKeys) {
        this.primaryKeys = primaryKeys;
    }

    public List<Index> getIndexes() {
        return indexes;
    }

    public void setIndexes(List<Index> indexes) {
        this.indexes = indexes;
    }

    
}
