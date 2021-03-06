package com.github.javahello.erm.generator.core.api;

import com.github.javahello.erm.generator.core.TestFileHelper;
import com.github.javahello.erm.generator.core.config.ErmConfigurationParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ErmMyBatisGeneratorTest {

    @Test
    @DisplayName("测试生成实体类")
    public void testGen()
            throws XMLParserException, IOException, InvalidConfigurationException, SQLException, InterruptedException {
        File configurationFile = new File(TestFileHelper.cpFilePath("config/generatorConfig-erm.xml"));

        List<String> warnings = new ArrayList<>();
        ErmConfigurationParser cp = new ErmConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configurationFile);

        DefaultShellCallback shellCallback = new DefaultShellCallback(true);

        ErmMyBatisGenerator myBatisGenerator = new ErmMyBatisGenerator(config, shellCallback, warnings);
        myBatisGenerator.generate(null, null, null, true);
        List<GeneratedJavaFile> generatedJavaFiles = myBatisGenerator.getGeneratedJavaFiles();
        Assertions.assertEquals(3, generatedJavaFiles.size());
    }

}
