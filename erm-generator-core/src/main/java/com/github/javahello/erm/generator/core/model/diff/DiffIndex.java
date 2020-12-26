package com.github.javahello.erm.generator.core.model.diff;

import com.github.javahello.erm.generator.core.model.db.Index;

/**
 * 差异索引
 * @author kaiv2
 */
public class DiffIndex {
    private String indexName;

    private Index oldIndex;
    private Index newIndex;

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public Index getOldIndex() {
        return oldIndex;
    }

    public void setOldIndex(Index oldIndex) {
        this.oldIndex = oldIndex;
    }

    public Index getNewIndex() {
        return newIndex;
    }

    public void setNewIndex(Index newIndex) {
        this.newIndex = newIndex;
    }

    
}
