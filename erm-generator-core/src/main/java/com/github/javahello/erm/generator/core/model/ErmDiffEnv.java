package com.github.javahello.erm.generator.core.model;

import com.github.javahello.erm.generator.core.codegen.ddl.DbType;

import java.util.List;
import java.util.Properties;

public class ErmDiffEnv {
    private DbType dbType = DbType.MYSQL;
    private String dbName;
    private List<String> newErmList;
    private List<String> oldErmList;
    private String outFilePath;

    private String templateFile;
    private Properties extraProperties;


    public Properties getExtraProperties() {
        return extraProperties;
    }

    public void setExtraProperties(Properties extraProperties) {
        this.extraProperties = extraProperties;
    }

    public void setNewErmList(List<String> newErmList) {
        this.newErmList = newErmList;
    }

    public List<String> getNewErmList() {
        return newErmList;
    }

    public List<String> getOldErmList() {
        return oldErmList;
    }

    public void setOldErmList(List<String> oldErmList) {
        this.oldErmList = oldErmList;
    }

    public DbType getDbType() {
        return dbType;
    }

    public void setDbType(DbType dbType) {
        this.dbType = dbType;
    }

    public String getOutFilePath() {
        return outFilePath;
    }

    public void setOutFilePath(String outFilePath) {
        this.outFilePath = outFilePath;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getTemplateFile() {
        return templateFile;
    }

    public void setTemplateFile(String templateFile) {
        this.templateFile = templateFile;
    }
}
