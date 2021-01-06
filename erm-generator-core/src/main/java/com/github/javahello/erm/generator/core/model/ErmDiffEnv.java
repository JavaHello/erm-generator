package com.github.javahello.erm.generator.core.model;

import com.github.javahello.erm.generator.core.codegen.ddl.DbType;

import java.util.List;
import java.util.Properties;

public class ErmDiffEnv {
    private String dbType = DbType.MYSQL.getCode();
    private String dbName;
    private List<String> newErmList;
    private List<String> oldErmList;
    private String outDDLFilePath;
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

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getOutDDLFilePath() {
        return outDDLFilePath;
    }

    public void setOutDDLFilePath(String outDDLFilePath) {
        this.outDDLFilePath = outDDLFilePath;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }
}
