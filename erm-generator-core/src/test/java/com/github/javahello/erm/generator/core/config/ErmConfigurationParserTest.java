package com.github.javahello.erm.generator.core.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.github.javahello.erm.generator.core.TestFileHelper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.exception.XMLParserException;


public class ErmConfigurationParserTest {

    @Test
    @DisplayName("测试解析配置文件")
    public void testParse() throws XMLParserException, IOException {

        Properties env = new Properties();
        List<String> warnings = new ArrayList<>();
        ErmConfigurationParser cp = new ErmConfigurationParser(env, warnings);
        File configurationFile = new File(TestFileHelper.cpFilePath("config/generatorConfig.xml"));
        Configuration config = cp.parseConfiguration(configurationFile);

        assertNotNull(config);


    }

}
