package com.github.javahello.erm.generator.core.codegen.ddl.mysql.impl;

import com.github.javahello.erm.generator.core.codegen.ddl.ICovDDL;
import com.github.javahello.erm.generator.core.codegen.ddl.ISqlTableNew;
import com.github.javahello.erm.generator.core.codegen.ddl.mysql.MySqlDDLHelper;
import com.github.javahello.erm.generator.core.model.db.Column;
import com.github.javahello.erm.generator.core.model.db.Index;
import com.github.javahello.erm.generator.core.model.db.Table;
import com.github.javahello.erm.generator.core.util.DiffHelper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author kaiv2
 */
public class MysqlTableNewGenImpl extends AbstractMysqlCovDDL<MysqlTableDelGenImpl> implements ISqlTableNew {

    private Table table;

    public MysqlTableNewGenImpl() {
    }


    @Override
    public String covDDL() {
        if (table == null) {
            return "";
        }
        StringBuilder tableStr = new StringBuilder();
        tableStr.append("CREATE TABLE ").append(table.getTableName()).append(" (").append("\n");
        // 列

        String columnsStr = table.getColumns().stream().map(column -> MySqlDDLHelper.INDENT + column.getColumnName() + MySqlDDLHelper.columnGe(column))
                .collect(Collectors.joining(",\n"));
        tableStr.append(columnsStr);
        List<Column> primaryKeys = table.getPrimaryKeys();
        // 主键
        if (!DiffHelper.isEmpty(primaryKeys)) {
            tableStr.append(",\n");
            tableStr.append(MySqlDDLHelper.INDENT).append("PRIMARY KEY (")
                    .append(primaryKeys.stream().map(Column::getColumnName).collect(Collectors.joining(", ")))
                    .append(")");
        } else {
            tableStr.append("\n没有主键,请自行确认\n");
        }

        // 索引
        List<Index> indexs = table.getIndexs();
        if (!DiffHelper.isEmpty(indexs)) {
            tableStr.append(",\n");
            String indexsStr = indexs.stream().map(index -> MySqlDDLHelper.INDENT + index.getIndexName() + MySqlDDLHelper.indexGe(index)).collect(Collectors.joining(",\n"));
            tableStr.append(indexsStr);
        }
        tableStr.append("\n) ");
        String tableComment = table.getTableComment();
        if (!DiffHelper.isEmpty(tableComment)) {
            tableStr.append("COMMENT = '").append(tableComment).append("'");
        }
        tableStr.append("\n");
        tableStr.append(otherInfo());
        tableStr.append("\n;");
        return tableStr.toString();
    }

    protected String otherInfo() {
        return "ENGINE=InnoDB";
    }

    @Override
    public ICovDDL newTable(Table table) {
        this.table = table;
        Optional.ofNullable(fixDdl).ifPresent(f -> f.delTable(table));
        return this;
    }

}
