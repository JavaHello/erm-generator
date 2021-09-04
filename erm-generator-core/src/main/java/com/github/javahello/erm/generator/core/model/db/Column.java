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
    private Boolean autoIncrement;
    private String defaultValue;
    private Integer length;
    private Integer decimal;
    private Boolean primaryKey;
    private Boolean uniqueKey;
    private Boolean notNull;
    private Boolean unsigned;

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

    public Boolean isAutoIncrement() {
        return autoIncrement;
    }

    public void setAutoIncrement(Boolean autoIncrement) {
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

    public Boolean isPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(Boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    public Boolean isUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(Boolean uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

    public Boolean isNotNull() {
        return notNull;
    }

    public void setNotNull(Boolean notNull) {
        this.notNull = notNull;
    }

    public Boolean isUnsigned() {
        return unsigned;
    }

    public void setUnsigned(Boolean unsigned) {
        this.unsigned = unsigned;
    }
}
