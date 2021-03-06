package com.github.javahello.erm.generator.core.model.db;

import java.util.List;

/**
 * @author kaiv2
 */
public class Index {
    private String id;
    private String indexName;
    private Boolean nonUnique;
    private List<Column> columns;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public Boolean isNonUnique() {
        return nonUnique;
    }

    public void setNonUnique(Boolean nonUnique) {
        this.nonUnique = nonUnique;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

}
