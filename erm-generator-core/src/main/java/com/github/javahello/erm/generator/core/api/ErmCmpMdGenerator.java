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
import com.github.javahello.erm.generator.core.model.diff.DiffColumn;
import com.github.javahello.erm.generator.core.model.diff.DiffEnum;
import com.github.javahello.erm.generator.core.model.diff.DiffIndex;
import com.github.javahello.erm.generator.core.model.diff.DiffTable;
import com.github.javahello.erm.generator.core.tbdiff.DefaultTableListDiffProcess;
import com.github.javahello.erm.generator.core.tbdiff.ITableListDiff;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author kaiv2
 */
public class ErmCmpMdGenerator extends AbstractGenerator {

    private String outFileName;

    protected ITableListDiff tableListDiff = new DefaultTableListDiffProcess();

    protected MdBuilder newTbMdBuilder = MdBuilder.newBuild();

    MdTableHead[] tableHeads = new MdTableHead[]{
            new MdTableHead("描述(新)", MdTableToward.C),
            new MdTableHead("名称(新)", MdTableToward.C),
            new MdTableHead("类型(新)", MdTableToward.C),
            new MdTableHead("长度(新)", MdTableToward.C),
            new MdTableHead("精度(新)", MdTableToward.C),
            new MdTableHead("不为空(新)", MdTableToward.C),
            new MdTableHead("自增(新)", MdTableToward.C),
            new MdTableHead("主键(新)", MdTableToward.C),
            new MdTableHead("默认值(新)", MdTableToward.C),
            new MdTableHead("-------", MdTableToward.C),
            new MdTableHead("描述(旧)", MdTableToward.C),
            new MdTableHead("名称(旧)", MdTableToward.C),
            new MdTableHead("类型(旧)", MdTableToward.C),
            new MdTableHead("长度(旧)", MdTableToward.C),
            new MdTableHead("精度(旧)", MdTableToward.C),
            new MdTableHead("不为空(旧)", MdTableToward.C),
            new MdTableHead("自增(旧)", MdTableToward.C),
            new MdTableHead("主键(旧)", MdTableToward.C),
            new MdTableHead("默认值(旧)", MdTableToward.C),
    };

    MdTableHead[] indexHeads = new MdTableHead[]{
            new MdTableHead("名称(新)", MdTableToward.C),
            new MdTableHead("字段(新)", MdTableToward.C),
            new MdTableHead("唯一(新)", MdTableToward.C),
            new MdTableHead("-------", MdTableToward.C),
            new MdTableHead("名称(旧)", MdTableToward.C),
            new MdTableHead("字段(旧)", MdTableToward.C),
            new MdTableHead("唯一(旧)", MdTableToward.C),
    };

    MdTableHead[] pkHeads = new MdTableHead[]{
            new MdTableHead("字段(新)", MdTableToward.C),
            new MdTableHead("-------", MdTableToward.C),
            new MdTableHead("字段(旧)", MdTableToward.C),
    };

    public ErmCmpMdGenerator(ErmDiffEnv ermDiffEnv) {
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
            outFileName = ermDiffEnv.getOutFilePath() + File.separator + ermDiffEnv.getDbName() + "_diff.md";
        }
    }

    @Override
    protected void runExec() throws Exception {
        Optional<List<DiffTable>> diffAllOpt = tableListDiff.diff(newCache.getTables(), oldCache.getTables());
        diffAllOpt.ifPresent(diffAll -> {
            for (DiffTable diffTable : diffAll) {
                if (diffTable.getDiffEnum() == DiffEnum.A) {
                    newTbMdBuilder.addTitle(MdTitleLevel.L2, diffTable.getNewTableName() + " 新增表结构");
                } else if (diffTable.getDiffEnum() == DiffEnum.D) {
                    newTbMdBuilder.addTitle(MdTitleLevel.L2, diffTable.getOldTableName() + " 删除表结构");
                } else if (diffTable.getDiffEnum() == DiffEnum.M) {
                    newTbMdBuilder.addTitle(MdTitleLevel.L2, diffTable.getNewTableName() + " 修改表结构");
                }
                TableMdNode tableMdNode = newTbMdBuilder.addTable();
                tableMdNode.addHeads(tableHeads);
                for (DiffColumn diffColumn : diffTable.getDiffColumns()) {
                    Column newColumn = Optional.ofNullable(diffColumn.getNewColumn()).orElseGet(Column::new);
                    Column oldColumn = Optional.ofNullable(diffColumn.getOldColumn()).orElseGet(Column::new);
                    tableMdNode.nextTr()
                            .addTd(newColumn.getColumnComment())
                            .addTd(newColumn.getColumnName())
                            .addTd(newColumn.getColumnType())
                            .addTd(newColumn.getLength())
                            .addTd(newColumn.getDecimal())
                            .addTd(newColumn.isNotNull())
                            .addTd(newColumn.isAutoIncrement())
                            .addTd(newColumn.isPrimaryKey())
                            .addTd(newColumn.getDefaultValue())
                            .addTd("--")
                            .addTd(oldColumn.getColumnComment())
                            .addTd(oldColumn.getColumnName())
                            .addTd(oldColumn.getColumnType())
                            .addTd(oldColumn.getLength())
                            .addTd(oldColumn.getDecimal())
                            .addTd(oldColumn.isNotNull())
                            .addTd(oldColumn.isAutoIncrement())
                            .addTd(oldColumn.isPrimaryKey())
                            .addTd(oldColumn.getDefaultValue());
                }

                if (diffTable.getDiffEnum() == DiffEnum.A) {
                    newTbMdBuilder.addTitle(MdTitleLevel.L2, diffTable.getNewTableName() + " 索引");
                } else if (diffTable.getDiffEnum() == DiffEnum.D) {
                    newTbMdBuilder.addTitle(MdTitleLevel.L2, diffTable.getOldTableName() + " 索引");
                } else if (diffTable.getDiffEnum() == DiffEnum.M) {
                    newTbMdBuilder.addTitle(MdTitleLevel.L2, diffTable.getNewTableName() + " 索引");
                }
                TableMdNode indexMdNode = newTbMdBuilder.addTable();
                indexMdNode.addHeads(indexHeads);
                for (DiffIndex diffIndex : diffTable.getDiffIndexs()) {
                    Index newIndex = Optional.ofNullable(diffIndex.getNewIndex()).orElseGet(Index::new);
                    Index oldIndex = Optional.ofNullable(diffIndex.getOldIndex()).orElseGet(Index::new);
                    indexMdNode.nextTr()
                            .addTd(newIndex.getIndexName())
                            .addTd(Optional.ofNullable(newIndex.getColumns()).orElseGet(ArrayList::new)
                                    .stream().map(Column::getColumnName).collect(Collectors.joining(", ")))
                            .addTd(Optional.ofNullable(newIndex.isNonUnique()).map(e -> !e).orElse(null))
                            .addTd("--")
                            .addTd(oldIndex.getIndexName())
                            .addTd(Optional.ofNullable(oldIndex.getColumns()).orElseGet(ArrayList::new)
                                    .stream().map(Column::getColumnName).collect(Collectors.joining(", ")))
                            .addTd(Optional.ofNullable(oldIndex.isNonUnique()).map(e -> !e).orElse(null));
                }


                if (diffTable.getDiffEnum() == DiffEnum.A) {
                    newTbMdBuilder.addTitle(MdTitleLevel.L2, diffTable.getNewTableName() + " 主键");
                } else if (diffTable.getDiffEnum() == DiffEnum.D) {
                    newTbMdBuilder.addTitle(MdTitleLevel.L2, diffTable.getOldTableName() + " 主键");
                } else if (diffTable.getDiffEnum() == DiffEnum.M) {
                    newTbMdBuilder.addTitle(MdTitleLevel.L2, diffTable.getNewTableName() + " 主键");
                }
                TableMdNode pkMdNode = newTbMdBuilder.addTable();
                pkMdNode.addHeads(pkHeads);
                for (DiffColumn diffPk : diffTable.getDiffPks()) {
                    Column newColumn = Optional.ofNullable(diffPk.getNewColumn()).orElseGet(Column::new);
                    Column oldColumn = Optional.ofNullable(diffPk.getOldColumn()).orElseGet(Column::new);
                    pkMdNode.nextTr()
                            .addTd(newColumn.getColumnName())
                            .addTd("--")
                            .addTd(oldColumn.getColumnName());
                }
            }
        });
    }


    @Override
    protected void afterExec() {
        super.afterExec();
        writeFileData(new File(outFileName), newTbMdBuilder.toMd());
    }
}
