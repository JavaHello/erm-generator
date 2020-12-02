package com.github.javahello.erm.generator.core.internal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Optional;

import com.github.javahello.erm.generator.core.TestFileHelper;
import com.github.javahello.erm.generator.core.model.db.Table;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 
 * @author kaiv2
 *
 */
class ErmReadTest {

    @Test
    @DisplayName("测试读取erm文件")
    void testRead() {
        ErmRead ermRead = new ErmRead(Arrays.asList(TestFileHelper.cpFilePath("erms/db.erm")));
        ermRead.read();
        assertFalse(ermRead.getErmList().isEmpty());
        
        Optional<Table> table = ermRead.getTable("SYS_CONFIG");
        assertTrue(table.isPresent());
    }

}
