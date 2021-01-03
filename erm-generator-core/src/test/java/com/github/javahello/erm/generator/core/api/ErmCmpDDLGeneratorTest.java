package com.github.javahello.erm.generator.core.api;

import com.github.javahello.erm.generator.core.TestFileHelper;
import com.github.javahello.erm.generator.core.model.ErmDDLEnv;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ErmCmpDDLGeneratorTest {

    @Test
    @DisplayName("测试比较生成DDL")
    public void testCmpGenDDL() {
        ErmDDLEnv env = new ErmDDLEnv();
        env.setNewErmList(Arrays.asList(TestFileHelper.cpFilePath("erms/db.erm")));
        env.setOldErmList(Arrays.asList(TestFileHelper.cpFilePath("erms/db2.erm")));
        ErmCmpDDLGenerator ermCmpDDLGenerator = new ErmCmpDDLGenerator(env) {
            @Override
            protected void afterExec() {
                super.afterExec();
                assertNotNull(allSql);
                System.out.println("DDL ---------------------------------------");
                System.out.println(allSql);
                System.out.println("FIX ---------------------------------------");
                System.out.println(currentOutDDL.fix().covDDL());
            }
        };
        ermCmpDDLGenerator.exec();
    }

}