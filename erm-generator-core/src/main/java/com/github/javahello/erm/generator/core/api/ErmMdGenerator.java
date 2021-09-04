package com.github.javahello.erm.generator.core.api;

import com.github.javahello.erm.generator.core.codegen.md.MdBuilder;
import com.github.javahello.erm.generator.core.codegen.md.MdTableHead;
import com.github.javahello.erm.generator.core.codegen.md.MdTableToward;
import com.github.javahello.erm.generator.core.codegen.md.MdTitleLevel;
import com.github.javahello.erm.generator.core.codegen.md.node.TableMdNode;
import com.github.javahello.erm.generator.core.model.ErmDiffEnv;
import com.github.javahello.erm.generator.core.model.db.Column;
import com.github.javahello.erm.generator.core.model.db.Index;
import com.github.javahello.erm.generator.core.model.db.Table;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author kaiv2
 */
public class ErmMdGenerator extends AbstractGenerator {

    private String outFileName;

    protected MdBuilder newTbMdBuilder = MdBuilder.newBuild();

    MdTableHead[] tableHeads = new MdTableHead[]{
            new MdTableHead("描述", MdTableToward.C),
            new MdTableHead("名称", MdTableToward.C),
            new MdTableHead("类型", MdTableToward.C),
            new MdTableHead("长度", MdTableToward.C),
            new MdTableHead("精度", MdTableToward.C),
            new MdTableHead("unsigned", MdTableToward.C),
            new MdTableHead("不为空", MdTableToward.C),
            new MdTableHead("自增", MdTableToward.C),
            new MdTableHead("主键", MdTableToward.C),
            new MdTableHead("默认值", MdTableToward.C),
    };

    MdTableHead[] indexHeads = new MdTableHead[]{
            new MdTableHead("名称", MdTableToward.C),
            new MdTableHead("字段", MdTableToward.C),
            new MdTableHead("唯一", MdTableToward.C),
    };

    public ErmMdGenerator(ErmDiffEnv ermDiffEnv) {
        super(ermDiffEnv);
        newTbMdBuilder.addTitle(MdTitleLevel.L1, ermDiffEnv.getDbName());
    }

    public void setOutFileName(String outFileName) {
        this.outFileName = outFileName;
    }

    @Override
    protected void beforeExec() {
        super.beforeExec();
        if (outFileName == null) {
            outFileName = ermDiffEnv.getOutFilePath() + File.separator + ermDiffEnv.getDbName() + ".md";
        }
    }

    @Override
    protected void runExec() throws Exception {
        newTableOut();
    }

    private void newTableOut() {
        List<Table> tables = newCache.getTables();
        for (Table table : tables) {
            newTbMdBuilder.addTitle(MdTitleLevel.L2, table.getTableName() + " 表结构");
            TableMdNode tableMdNode = newTbMdBuilder.addTable();
            tableMdNode.addHeads(tableHeads);
            for (Column column : table.getColumns()) {
                tableMdNode.nextTr()
                        .addTd(column.getColumnComment())
                        .addTd(column.getColumnName())
                        .addTd(column.getColumnType())
                        .addTd(column.getLength())
                        .addTd(column.getDecimal())
                        .addTd(column.isUnsigned())
                        .addTd(column.isNotNull())
                        .addTd(column.isAutoIncrement())
                        .addTd(column.isPrimaryKey())
                        .addTd(column.getDefaultValue());
            }
            newTbMdBuilder.addTitle(MdTitleLevel.L2, table.getTableName() + " 索引");
            TableMdNode indexMdNode = newTbMdBuilder.addTable();
            indexMdNode.addHeads(indexHeads);
            for (Index index : table.getIndexes()) {
                indexMdNode.nextTr()
                        .addTd(index.getIndexName())
                        .addTd(index.getColumns().stream().map(Column::getColumnName).collect(Collectors.joining(", ")))
                        .addTd(!index.isNonUnique());
            }
        }
    }

    @Override
    protected void afterExec() {
        super.afterExec();
        writeFileData(new File(outFileName), newTbMdBuilder.toMd());
    }
}
