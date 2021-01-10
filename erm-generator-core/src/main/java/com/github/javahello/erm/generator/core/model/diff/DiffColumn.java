package com.github.javahello.erm.generator.core.model.diff;

import com.github.javahello.erm.generator.core.model.db.Column;

/**
 * @author kaiv2
 */
public class DiffColumn {

    private String newColumnName;
    private String newColumnComment;

    private String oldColumnName;
    private String oldColumnComment;

    private Column oldColumn;
    private Column newColumn;

    public String getNewColumnName() {
        return newColumnName;
    }

    public void setNewColumnName(String newColumnName) {
        this.newColumnName = newColumnName;
    }

    public String getNewColumnComment() {
        return newColumnComment;
    }

    public void setNewColumnComment(String newColumnComment) {
        this.newColumnComment = newColumnComment;
    }

    public String getOldColumnName() {
        return oldColumnName;
    }

    public void setOldColumnName(String oldColumnName) {
        this.oldColumnName = oldColumnName;
    }

    public String getOldColumnComment() {
        return oldColumnComment;
    }

    public void setOldColumnComment(String oldColumnComment) {
        this.oldColumnComment = oldColumnComment;
    }

    public Column getOldColumn() {
        return oldColumn;
    }

    public void setOldColumn(Column oldColumn) {
        this.oldColumn = oldColumn;
    }

    public Column getNewColumn() {
        return newColumn;
    }

    public void setNewColumn(Column newColumn) {
        this.newColumn = newColumn;
    }

    
}
