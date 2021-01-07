package com.github.javahello.erm.generator.core.codegen.ddl;

import java.util.Optional;

public enum DbType {
    MYSQL("MySQL"), ORACLE("Oracle");
    private String code;

    DbType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static Optional<DbType> of(String database) {
        for (DbType dbType : values()) {
            if (dbType.getCode().equalsIgnoreCase(database)) {
                return Optional.of(dbType);
            }
        }
        return Optional.empty();
    }
}
