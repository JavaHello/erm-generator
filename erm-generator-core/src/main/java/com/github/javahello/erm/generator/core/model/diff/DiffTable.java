package com.github.javahello.erm.generator.core.model.diff;

import java.util.List;

/**
 * @author kaiv2
 */
public class DiffTable {

    private String newTableName;
    private String newTableComment;

    private String oldTableName;
    private String oldTableComment;
    private DiffEnum diffEnum;

    private List<DiffColumn> diffColumns;

    private List<DiffColumn> diffPks;

    private List<DiffIndex> diffIndexs;

    public String getNewTableName() {
        return newTableName;
    }

    public void setNewTableName(String newTableName) {
        this.newTableName = newTableName;
    }

    public String getNewTableComment() {
        return newTableComment;
    }

    public void setNewTableComment(String newTableComment) {
        this.newTableComment = newTableComment;
    }

    public String getOldTableName() {
        return oldTableName;
    }

    public void setOldTableName(String oldTableName) {
        this.oldTableName = oldTableName;
    }

    public String getOldTableComment() {
        return oldTableComment;
    }

    public void setOldTableComment(String oldTableComment) {
        this.oldTableComment = oldTableComment;
    }

    public DiffEnum getDiffEnum() {
        return diffEnum;
    }

    public void setDiffEnum(DiffEnum diffEnum) {
        this.diffEnum = diffEnum;
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
