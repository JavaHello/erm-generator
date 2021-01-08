package com.github.javahello.erm.generator.core.api;

import com.github.javahello.erm.generator.core.TestFileHelper;
import com.github.javahello.erm.generator.core.model.ErmDiffEnv;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;

class ErmExcelGeneratorTest {

    @Test
    @DisplayName("测试生成Excel")
    public void testGexExcel() {
        ErmDiffEnv env = new ErmDiffEnv();
        env.setNewErmList(Arrays.asList(TestFileHelper.cpFilePath("erms/db.erm")));
        env.setOldErmList(Arrays.asList(TestFileHelper.cpFilePath("erms/db2.erm")));
        env.setOutFilePath(new File(TestFileHelper.cpFilePath("erms/db.erm")).getParent());
        env.setDbName("demo");
        AbstractGenerator ermExcelGenerator = new ErmExcelGenerator(env);
        ermExcelGenerator.exec();
    }

}