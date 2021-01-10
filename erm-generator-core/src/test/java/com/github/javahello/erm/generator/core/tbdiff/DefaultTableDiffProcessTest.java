package com.github.javahello.erm.generator.core.tbdiff;


import com.alibaba.fastjson.JSON;
import com.github.javahello.erm.generator.core.model.db.Column;
import com.github.javahello.erm.generator.core.model.db.Index;
import com.github.javahello.erm.generator.core.model.db.Table;
import com.github.javahello.erm.generator.core.model.diff.DiffColumn;
import com.github.javahello.erm.generator.core.model.diff.DiffEnum;
import com.github.javahello.erm.generator.core.model.diff.DiffIndex;
import com.github.javahello.erm.generator.core.model.diff.DiffTable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DefaultTableDiffProcessTest {

    @Test
    @DisplayName("Table差异比较")
    public void testDiff() {
        DefaultTableDiffProcess tableDiffProcess = new DefaultTableDiffProcess();
        Optional<DiffTable> diffTable = tableDiffProcess.diff(null, null);
        assertFalse(diffTable.isPresent());

        Table t1 = new Table();
        Table t2 = new Table();
        diffTable = tableDiffProcess.diff(t1, t2);
        assertFalse(diffTable.isPresent());


        t1 = new Table();
        t2 = new Table();
        t1.setTableName("t1");
        diffTable = tableDiffProcess.diff(t1, t2);
        assertTrue(diffTable.isPresent());

        t1 = new Table();
        t2 = new Table();
        t1.setTableName("t1");

        List<Column> cols1 = new ArrayList<>();
        Column col1 = new Column();
        col1.setColumnName("aaa");
        cols1.add(col1);
        t1.setColumns(cols1);
        t2.setColumns(new ArrayList<>());

        List<Index> indices1 = new ArrayList<>();
        Index idx1 = new Index();
        idx1.setColumns(cols1);
        idx1.setIndexName("test");
        indices1.add(idx1);
        t1.setIndexes(indices1);

        t1.setPrimaryKeys(cols1);

        diffTable = tableDiffProcess.diff(t1, t2);
        assertTrue(diffTable.isPresent());
        DiffTable dt = diffTable.get();
        assertSame(dt.getDiffEnum(), DiffEnum.A);

        List<DiffColumn> diffColumns = dt.getDiffColumns();
        assertNotNull(diffColumns);
        assertTrue(diffColumns.stream().map(DiffColumn::getNewColumn).map(Column::getColumnName).allMatch("aaa"::equals));

        List<DiffIndex> diffIndexList = dt.getDiffIndexs();
        assertNotNull(diffIndexList);
        assertTrue(diffIndexList.stream().map(DiffIndex::getNewIndex).map(Index::getIndexName).allMatch("test"::equals));


        List<DiffColumn> pks = dt.getDiffPks();
        assertNotNull(pks);
        assertTrue(pks.stream().map(DiffColumn::getNewColumn).map(Column::getColumnName).allMatch("aaa"::equals));
    }

    private void show(Optional<DiffTable> diffTable) {
        System.out.println(JSON.toJSON(diffTable.orElse(null)));
    }
}