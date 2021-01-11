package com.github.javahello.erm.generator.core.api;

import com.github.javahello.erm.generator.core.TestFileHelper;
import com.github.javahello.erm.generator.core.model.ErmDiffEnv;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ErmCmpMdGeneratorTest {

    @Test
    public void testOutCmpMd() {
        ErmDiffEnv env = new ErmDiffEnv();
        env.setDbName("demo");
        env.setNewErmList(Arrays.asList(TestFileHelper.cpFilePath("erms/db.erm")));
        env.setOldErmList(Arrays.asList(TestFileHelper.cpFilePath("erms/db2.erm")));
        ErmCmpMdGenerator ermCmpMdGenerator = new ErmCmpMdGenerator(env) {
            @Override
            protected void afterExec() {
                String md = newTbMdBuilder.toMd();
                assertNotNull(md);
                System.out.println(md);
            }
        };
        ermCmpMdGenerator.exec();

    }
}