package com.github.javahello.erm.generator.core.model.db;

import java.util.List;


/**
 * @author kaiv2
 */
public class Table {
    private String tablesName;
    private String tableComment;
    private List<Column> columns;
    private List<Column> primaryKeys;
    private List<Index> indexs;

    public String getTablesName() {
        return tablesName;
    }

    public void setTablesName(String tablesName) {
        this.tablesName = tablesName;
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

    public List<Index> getIndexs() {
        return indexs;
    }

    public void setIndexs(List<Index> indexs) {
        this.indexs = indexs;
    }

    
}
