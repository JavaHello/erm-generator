package com.github.javahello.erm.generator.core.codegen.ddl.mysql;

import com.github.javahello.erm.generator.core.TestFileHelper;
import com.github.javahello.erm.generator.core.internal.ErmRead;
import com.github.javahello.erm.generator.core.model.diff.DiffEnum;
import com.github.javahello.erm.generator.core.model.diff.DiffTable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class GenMysqlDDLTest {

    @Test
    @DisplayName("测试生成DDL")
    public void testGenDDL() {

        ErmRead ermRead = new ErmRead(Arrays.asList(TestFileHelper.cpFilePath("erms/db.erm")));
        ermRead.read();
        List<DiffTable> diffTables = new ArrayList<>();
        DiffTable diffTable = new DiffTable();
        diffTable.setDiffEnum(DiffEnum.A);
        diffTable.setNewTableName("TEST_MYSQL");
        diffTables.add(diffTable);
        GenMysqlDDL genMysqlDDL = new GenMysqlDDL(ermRead, diffTables);
        String ddl = genMysqlDDL.covDDL();
        Assertions.assertNotNull(ddl);
        Assertions.assertFalse(ddl.isEmpty());
        System.out.println("DDL ------------------------------------");
        System.out.println(ddl);
        System.out.println("FIX ------------------------------------");
        System.out.println(genMysqlDDL.fix().covDDL());
    }

}