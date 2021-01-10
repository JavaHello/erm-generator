package com.github.javahello.erm.generator.core.api;

import com.github.javahello.erm.generator.core.TestFileHelper;
import com.github.javahello.erm.generator.core.model.ErmDiffEnv;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class ErmCmpDDLGeneratorTest {

    @Test
    @DisplayName("测试比较生成DDL")
    public void testCmpGenDDL() {
        ErmDiffEnv env = new ErmDiffEnv();
        env.setDbName("demo");
        env.setNewErmList(Arrays.asList(TestFileHelper.cpFilePath("erms/db.erm")));
        env.setOldErmList(Arrays.asList(TestFileHelper.cpFilePath("erms/db2.erm")));
        ErmCmpDDLGenerator ermCmpDDLGenerator = new ErmCmpDDLGenerator(env) {
            @Override
            protected void afterExec() {
                assertNotNull(allSql);
                System.out.println("DDL ---------------------------------------");
                System.out.println(allSql);
                System.out.println("FIX ---------------------------------------");
                System.out.println(currentOutDDL.fix().covDDL());
            }

            @Override
            protected void errorExec(Exception exception) {
                Optional.ofNullable(exception).ifPresent(Exception::printStackTrace);
                assertNull(exception);
            }
        };
        ermCmpDDLGenerator.exec();
    }

}