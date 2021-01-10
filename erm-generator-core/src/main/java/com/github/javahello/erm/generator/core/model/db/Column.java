package com.github.javahello.erm.generator.core.model.db;

/**
 * @author kaiv2
 */
public class Column {
    private String id;
    private String columnName;
    private String columnComment;
    private int jdbcType;
    private String columnType;
    private boolean autoIncrement;
    private String defaultValue;
    private Integer length;
    private Integer decimal;
    private boolean primaryKey;
    private boolean uniqueKey;
    private boolean notNull;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public int getJdbcType() {
        return jdbcType;
    }

    public void setJdbcType(int jdbcType) {
        this.jdbcType = jdbcType;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public boolean isAutoIncrement() {
        return autoIncrement;
    }

    public void setAutoIncrement(boolean autoIncrement) {
        this.autoIncrement = autoIncrement;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getDecimal() {
        return decimal;
    }

    public void setDecimal(Integer decimal) {
        this.decimal = decimal;
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    public boolean isUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(boolean uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

    public boolean isNotNull() {
        return notNull;
    }

    public void setNotNull(boolean notNull) {
        this.notNull = notNull;
    }

}
