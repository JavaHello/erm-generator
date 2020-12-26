package com.github.javahello.erm.generator.core.model.diff;

import com.github.javahello.erm.generator.core.model.db.Column;

/**
 * @author kaiv2
 */
public class DiffColumn {

    private String columnName;
    private String columnComment;

    private Column oldColumn;
    private Column newColumn;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
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
