package com.github.javahello.erm.generator.core.model.diff;

import com.github.javahello.erm.generator.core.model.db.Index;

/**
 * 差异索引
 * @author kaiv2
 */
public class DiffIndex {
    private String newIndexName;
    private String oldIndexName;

    private Index oldIndex;
    private Index newIndex;

    public String getNewIndexName() {
        return newIndexName;
    }

    public void setNewIndexName(String newIndexName) {
        this.newIndexName = newIndexName;
    }

    public String getOldIndexName() {
        return oldIndexName;
    }

    public void setOldIndexName(String oldIndexName) {
        this.oldIndexName = oldIndexName;
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
