package com.github.javahello.erm.generator.core.codegen.ddl.mysql;

import com.github.javahello.erm.generator.core.TestFileHelper;
import com.github.javahello.erm.generator.core.internal.ErmRead;
import com.github.javahello.erm.generator.core.internal.TableCache;
import com.github.javahello.erm.generator.core.model.db.Table;
import com.github.javahello.erm.generator.core.model.diff.DiffTable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class GenMysqlDDLTest {

    @Test
    @DisplayName("测试生成DDL")
    public void testGenDDL() {

        ErmRead ermRead = new ErmRead(Arrays.asList(TestFileHelper.cpFilePath("erms/db.erm")));
        ermRead.read();
        List<DiffTable> diffTables = new ArrayList<>();
        DiffTable diffTable = new DiffTable();
        diffTable.setNewTb(true);
        diffTable.setTableName("SYS_CONFIG");
        diffTables.add(diffTable);
        GenMysqlDDL genMysqlDDL = new GenMysqlDDL(ermRead, diffTables);
        String ddl = genMysqlDDL.covDDL();
        System.out.println(ddl);
    }

}