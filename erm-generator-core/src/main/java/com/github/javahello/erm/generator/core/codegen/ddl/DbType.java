package com.github.javahello.erm.generator.core.codegen.ddl;

public enum DbType {
    MYSQL("MySQL"), ORACLE("Oracle");
    private String code;

    DbType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
