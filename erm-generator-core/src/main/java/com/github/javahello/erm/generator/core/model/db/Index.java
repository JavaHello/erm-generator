package com.github.javahello.erm.generator.core.model.db;

import java.util.List;

/**
 * @author kaiv2
 */
public class Index {
    private String indexName;
    private boolean nonUnique;
    private List<Column> columns;

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public boolean isNonUnique() {
        return nonUnique;
    }

    public void setNonUnique(boolean nonUnique) {
        this.nonUnique = nonUnique;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

}
