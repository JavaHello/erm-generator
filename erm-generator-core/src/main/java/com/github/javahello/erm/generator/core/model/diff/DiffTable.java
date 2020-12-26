package com.github.javahello.erm.generator.core.model.diff;

import java.util.List;

/**
 * @author kaiv2
 */
public class DiffTable {

    private String tableName;
    private String tableComment;

    private boolean newTb;

    private List<DiffColumn> diffColumns;

    private List<DiffColumn> diffPks;

    private List<DiffIndex> diffIndexs;

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

    public boolean isNewTb() {
        return newTb;
    }

    public void setNewTb(boolean newTb) {
        this.newTb = newTb;
    }

    public List<DiffColumn> getDiffColumns() {
        return diffColumns;
    }

    public void setDiffColumns(List<DiffColumn> diffColumns) {
        this.diffColumns = diffColumns;
    }

    public List<DiffColumn> getDiffPks() {
        return diffPks;
    }

    public void setDiffPks(List<DiffColumn> diffPks) {
        this.diffPks = diffPks;
    }

    public List<DiffIndex> getDiffIndexs() {
        return diffIndexs;
    }

    public void setDiffIndexs(List<DiffIndex> diffIndexs) {
        this.diffIndexs = diffIndexs;
    }

}
