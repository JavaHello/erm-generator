package com.github.javahello.erm.generator.core.internal.sqltype;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 
 * @author kaiv2
 * 
 */
public abstract class TypeMap {

    protected static Map<String, Integer> TYPE_MAP = new HashMap<>();
    protected static Map<String, Integer> STRING_TYPE_MAP = new HashMap<>();

    static {
        // 注意大写
        TYPE_MAP.put("ARRAY", Types.ARRAY);
        TYPE_MAP.put("BIGINT", Types.BIGINT);
        TYPE_MAP.put("BINARY", Types.BINARY);
        TYPE_MAP.put("BIT", Types.BIT);
        TYPE_MAP.put("BLOB", Types.BLOB);
        TYPE_MAP.put("BOOLEAN", Types.BOOLEAN);
        TYPE_MAP.put("CHAR", Types.CHAR);
        TYPE_MAP.put("CLOB", Types.CLOB);
        TYPE_MAP.put("DATALINK", Types.DATALINK);
        TYPE_MAP.put("DATE", Types.DATE);
        TYPE_MAP.put("DECIMAL", Types.DECIMAL);
        TYPE_MAP.put("DISTINCT", Types.DISTINCT);
        TYPE_MAP.put("DOUBLE", Types.DOUBLE);
        TYPE_MAP.put("FLOAT", Types.FLOAT);
        TYPE_MAP.put("INTEGER", Types.INTEGER);
        TYPE_MAP.put("JAVA_OBJECT", Types.JAVA_OBJECT);
        TYPE_MAP.put("LONGNVARCHAR", Types.LONGNVARCHAR);
        TYPE_MAP.put("LONGVARBINARY", Types.LONGVARBINARY);
        TYPE_MAP.put("LONGVARCHAR", Types.LONGVARCHAR);
        TYPE_MAP.put("NCHAR", Types.NCHAR);
        TYPE_MAP.put("NCLOB", Types.NCLOB);
        TYPE_MAP.put("NVARCHAR", Types.NVARCHAR);
        TYPE_MAP.put("NULL", Types.NULL);
        TYPE_MAP.put("NUMERIC", Types.NUMERIC);
        TYPE_MAP.put("OTHER", Types.OTHER);
        TYPE_MAP.put("REAL", Types.REAL);
        TYPE_MAP.put("REF", Types.REF);
        TYPE_MAP.put("SMALLINT", Types.SMALLINT);
        TYPE_MAP.put("STRUCT", Types.STRUCT);
        TYPE_MAP.put("TIME", Types.TIME);
        TYPE_MAP.put("TIMESTAMP", Types.TIMESTAMP);
        TYPE_MAP.put("TINYINT", Types.TINYINT);
        TYPE_MAP.put("VARBINARY", Types.VARBINARY);
        TYPE_MAP.put("VARCHAR", Types.VARCHAR);
        // JDK 1.8 types
        TYPE_MAP.put("TIME_WITH_TIMEZONE", Types.TIME_WITH_TIMEZONE);
        TYPE_MAP.put("TIMESTAMP_WITH_TIMEZONE", Types.TIMESTAMP_WITH_TIMEZONE);

        // MySQL
        TYPE_MAP.put("DATETIME", Types.TIMESTAMP);
        TYPE_MAP.put("INT", Types.INTEGER);

        TYPE_MAP.put("TINYTEXT", Types.VARCHAR);
        TYPE_MAP.put("TEXT", Types.VARCHAR);
        TYPE_MAP.put("MEDIUMTEXT", Types.VARCHAR);
        TYPE_MAP.put("LONGTEXT", Types.VARCHAR);

    }

    static {
        // 注意大写
        STRING_TYPE_MAP.put("CHAR", Types.CHAR);
        STRING_TYPE_MAP.put("LONGNVARCHAR", Types.LONGNVARCHAR);
        STRING_TYPE_MAP.put("LONGVARCHAR", Types.LONGVARCHAR);
        STRING_TYPE_MAP.put("NCHAR", Types.NCHAR);
        STRING_TYPE_MAP.put("NVARCHAR", Types.NVARCHAR);
        STRING_TYPE_MAP.put("VARBINARY", Types.VARBINARY);
        STRING_TYPE_MAP.put("VARCHAR", Types.VARCHAR);

        // MySQL
        TYPE_MAP.put("TINYTEXT", Types.VARCHAR);
        TYPE_MAP.put("TEXT", Types.VARCHAR);
        TYPE_MAP.put("MEDIUMTEXT", Types.VARCHAR);
        TYPE_MAP.put("LONGTEXT", Types.VARCHAR);
    }

    public static boolean isStringType(String type) {
        return STRING_TYPE_MAP.containsKey(type);
    }

    public static int jdbcType(String type) {
        return Optional.ofNullable(TYPE_MAP.get(type.toUpperCase()))
                .orElseThrow(() -> new IllegalArgumentException("类型错误: " + type));
    }

}
