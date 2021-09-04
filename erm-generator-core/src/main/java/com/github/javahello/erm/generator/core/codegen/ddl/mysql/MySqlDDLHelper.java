package com.github.javahello.erm.generator.core.codegen.ddl.mysql;

import com.github.javahello.erm.generator.core.codegen.ddl.DbType;
import com.github.javahello.erm.generator.core.internal.sqltype.TypeMap;
import com.github.javahello.erm.generator.core.model.db.Column;
import com.github.javahello.erm.generator.core.model.db.Index;
import com.github.javahello.erm.generator.core.util.DiffHelper;

import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author kaiv2
 */
public abstract class MySqlDDLHelper {

    public static final String INDENT = "    ";

    private static String nullOpt(boolean isNotNull) {
        return isNotNull ? "NOT NULL" : "NULL";
    }
    private static String unsignedOpt(boolean unsigned) {
        return unsigned ? " UNSIGNED" : "";
    }

    private static String defaultValue(DbType dbType, Column column) {
        if (column.getDefaultValue() == null) {
            return "";
        }
        return " DEFAULT" + (TypeMap.isStringType(dbType, column.getColumnType()) ? warp(warp(column.getDefaultValue(), "'"), " ") : warp(column.getDefaultValue(), " "));
    }

    private static String comment(Column column) {
        if (DiffHelper.isEmpty(column.getColumnComment())) {
            return "";
        }
        return " COMMENT " + warp(column.getColumnComment(), "'");
    }


    private static String warp(String s, String d) {
        return d + s + d;
    }

    private static String warpPt(Object l, Object s) {
        return Optional.ofNullable(l).map(Object::toString)
                .map(e -> "(" + e + Optional.ofNullable(s)
                        .map(Object::toString)
                        .map(e2 -> "," + e2 + ")")
                        .orElse(")"))
                .orElse("");
    }

    public static String indexGe(Index index) {
        String opt = index.isNonUnique() ? "INDEX " : "UNIQUE INDEX ";
        return opt + index.getIndexName() + " ("
                + index.getColumns().stream().map(Column::getColumnName)
                .collect(Collectors.joining(", ")) + ")";
    }

    public static String columnGe(DbType dbType, Column column) {
        String colType = Optional.ofNullable(column.getLength()).map(len -> column.getColumnType() + warpPt(len, column.getDecimal())).orElse(column.getColumnType());
        return " " + colType + unsignedOpt(column.isUnsigned()) + " "
                + nullOpt(column.isNotNull()) + defaultValue(dbType, column) + comment(column);
    }
}
