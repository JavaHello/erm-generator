package com.github.javahello.erm.generator.core.codegen.ddl.mysql;

import com.github.javahello.erm.generator.core.model.db.Column;
import com.github.javahello.erm.generator.core.model.db.Index;
import com.github.javahello.erm.generator.core.util.DiffHelper;
import com.github.javahello.erm.generator.core.util.TypeMap;

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

    private static String defaultValue(Column column) {
        if (DiffHelper.isEmpty(column.getDefaultValue())) {
            return "";
        }
        return " DEFAULT" + (TypeMap.isStringType(column.getColumnType()) ? warp(warp(column.getDefaultValue(), "'"), " ") : warp(column.getDefaultValue(), " "));
    }

    private static String comment(Column column) {
        if (DiffHelper.isEmpty(column.getColumnComment())) {
            return "";
        }
        return " COMMENT " + warp(column.getColumnComment(), "\"");
    }


    private static String warp(String s, String d) {
        return d + s + d;
    }

    private static String warpPt(Object s) {
        return "(" + s + ")";
    }

    public static String indexGe(Index index) {
        String opt = index.isNonUnique() ? "INDEX " : "UNIQUE INDEX ";
        return opt + index.getIndexName() + " ("
                + index.getColumns().stream().map(Column::getColumnName)
                .collect(Collectors.joining(", ")) + ")";
    }

    public static String columnGe(Column column) {
        String colType = Optional.ofNullable(column.getLength()).map(len -> column.getColumnType() + warpPt(len)).orElse(column.getColumnType());
        return " " + colType + " "
                + nullOpt(column.isNotNull()) + defaultValue(column) + comment(column);
    }
}
