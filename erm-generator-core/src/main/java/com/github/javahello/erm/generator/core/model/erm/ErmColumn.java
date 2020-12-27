package com.github.javahello.erm.generator.core.model.erm;

/**
 * @author kaiv2
 */
public class ErmColumn {

    private String id;

    //    @XmlElement(name = "word_id")
    private String wordId;

    private String description;

    //    @XmlElement(name = "unique_key_name")
    private String uniqueKeyName;

    //    @XmlElement(name = "logical_name")
    private String logicalName;

    //    @XmlElement(name = "physical_name")
    private String physicalName;

    private String type;

    private String constraint;

    //    @XmlElement(name = "default_value")
    private String defaultValue;

    //    @XmlElement(name = "auto_increment")
    private boolean autoIncrement;

    //    @XmlElement(name = "foreign_key")
    private String foreignKey;

    //    @XmlElement(name = "not_null")
    private boolean notNull;

    //    @XmlElement(name = "primary_key")
    private boolean primaryKey;

    //    @XmlElement(name = "unique_key")
    private boolean uniqueKey;

    //    @XmlElement(name = "character_set")
    private String characterSet;

    private String collation;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    //    @XmlTransient
    public String getWordId() {
        return wordId;
    }

    public void setWordId(String wordId) {
        this.wordId = wordId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    //    @XmlTransient
    public String getUniqueKeyName() {
        return uniqueKeyName;
    }

    public void setUniqueKeyName(String uniqueKeyName) {
        this.uniqueKeyName = uniqueKeyName;
    }

    //    @XmlTransient
    public String getLogicalName() {
        return logicalName;
    }

    public void setLogicalName(String logicalName) {
        this.logicalName = logicalName;
    }

    //    @XmlTransient
    public String getPhysicalName() {
        return physicalName;
    }

    public void setPhysicalName(String physicalName) {
        this.physicalName = physicalName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getConstraint() {
        return constraint;
    }

    public void setConstraint(String constraint) {
        this.constraint = constraint;
    }

    //    @XmlTransient
    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    //    @XmlTransient
    public boolean getAutoIncrement() {
        return autoIncrement;
    }

    public void setAutoIncrement(boolean autoIncrement) {
        this.autoIncrement = autoIncrement;
    }

    //    @XmlTransient
    public String getForeignKey() {
        return foreignKey;
    }

    public void setForeignKey(String foreignKey) {
        this.foreignKey = foreignKey;
    }

    //    @XmlTransient
    public boolean getNotNull() {
        return notNull;
    }

    public void setNotNull(boolean notNull) {
        this.notNull = notNull;
    }

    //    @XmlTransient
    public boolean getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(Boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    //    @XmlTransient
    public boolean getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(boolean uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

    //    @XmlTransient
    public String getCharacterSet() {
        return characterSet;
    }

    public void setCharacterSet(String characterSet) {
        this.characterSet = characterSet;
    }

    public String getCollation() {
        return collation;
    }

    public void setCollation(String collation) {
        this.collation = collation;
    }

}
