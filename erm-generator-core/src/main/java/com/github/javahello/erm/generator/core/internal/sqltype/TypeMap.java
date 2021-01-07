package com.github.javahello.erm.generator.core.internal.sqltype;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.javahello.erm.generator.core.codegen.ddl.DbType;
import com.github.javahello.erm.generator.core.util.FileUtils;
import org.mybatis.generator.logging.Log;
import org.mybatis.generator.logging.LogFactory;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.nio.file.Files;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * @author kaiv2
 */
public abstract class TypeMap {

    private static final Log log = LogFactory.getLog(TypeMap.class);
    protected static Map<String, Integer> TYPE_MAP = new HashMap<>();
    protected static Map<String, Integer> STRING_TYPE_MAP = new HashMap<>();
    protected static Map<String, Map<String, Integer>> DB_TYPE_MAP = new HashMap<>();
    protected static Map<String, Map<String, Integer>> DB_STRING_TYPE_MAP = new HashMap<>();

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

        // 注意大写
        STRING_TYPE_MAP.put("CHAR", Types.CHAR);
        STRING_TYPE_MAP.put("LONGNVARCHAR", Types.LONGNVARCHAR);
        STRING_TYPE_MAP.put("LONGVARCHAR", Types.LONGVARCHAR);
        STRING_TYPE_MAP.put("NCHAR", Types.NCHAR);
        STRING_TYPE_MAP.put("NVARCHAR", Types.NVARCHAR);
        STRING_TYPE_MAP.put("VARBINARY", Types.VARBINARY);
        STRING_TYPE_MAP.put("VARCHAR", Types.VARCHAR);


        try {
            load();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }
    }

    private static void loadJdbcType() throws IllegalAccessException {
        Class<Types> typesClass = Types.class;
        Field[] declaredFields = typesClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            int modifiers = declaredField.getModifiers();
            if (Modifier.isStatic(modifiers) && Modifier.isPublic(modifiers)) {
                declaredField.setAccessible(true);
                // 加载变量
                TYPE_MAP.putIfAbsent(declaredField.getName(), declaredField.getInt(typesClass));
            }
        }
    }

    private static void load() throws Exception {
        loadJdbcType();
        String sqlType = "/sqltype/mysql.json";
        URL sqlTypeFile = TypeMap.class.getResource(sqlType);
        Objects.requireNonNull(sqlTypeFile, sqlType + " 找不到文件");
        File parentFile = new File(sqlTypeFile.getFile()).getParentFile();
        File[] listFiles = parentFile.listFiles();
        if (listFiles != null) {
            for (File file : listFiles) {
                final String name = file.getName();
                log.debug("SQL_TYPE加载: " + name);
                if (file.isFile() && FileUtils.isExtra(name, "json")) {
                    final String key = covName(FileUtils.fileName(name));
                    JSONObject typeJson = (JSONObject) JSON.parse(Files.readAllBytes(file.toPath()));
                    Optional.ofNullable(typeJson.getJSONObject("stringType")).ifPresent(t -> {
                        DB_STRING_TYPE_MAP.putIfAbsent(key, covTypeMap(key, t));
                    });
                    Optional.ofNullable(typeJson.getJSONObject("allType")).ifPresent(t -> {
                        DB_TYPE_MAP.putIfAbsent(key, covTypeMap(key, t));
                    });
                }
            }
        }
    }

    private static String covName(String name) {
        return name.toUpperCase();
    }

    private static Map<String, Integer> covTypeMap(String name, JSONObject jsonObject) {
        Map<String, Integer> result = new HashMap<>();
        for (String key : jsonObject.keySet()) {
            result.put(key, Objects.requireNonNull(TYPE_MAP.get(jsonObject.getString(key)), name + ", 错误的 TYPE: " + key));
        }
        return result;
    }

    public static boolean isStringType(DbType dbType, String type) {
        final String key = covName(type);
        return STRING_TYPE_MAP.containsKey(key) || Optional.ofNullable(DB_STRING_TYPE_MAP.get(covName(dbType.getCode()))).filter(e -> e.containsKey(key)).isPresent();
    }

    public static int jdbcType(DbType dbType, String type) {
        final String key = covName(type);
        return Optional.ofNullable(TYPE_MAP.get(key))
                .orElseGet(
                        () -> Optional.ofNullable(DB_TYPE_MAP.get(covName(dbType.getCode())))
                                .map(e -> e.get(key))
                                .orElseThrow(() -> new IllegalArgumentException("类型错误: " + type))
                );
    }

}
