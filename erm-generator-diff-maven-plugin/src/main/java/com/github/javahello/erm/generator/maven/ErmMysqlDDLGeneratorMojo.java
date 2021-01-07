/*
 *    Copyright 2006-2020 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.github.javahello.erm.generator.maven;

import com.github.javahello.erm.generator.core.api.ErmCmpDDLGenerator;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.util.Optional;

/**
 * 使用 generate-mysql-ddl 生成 erm 差异 SQL.
 */
@Mojo(name = "generate-mysql-ddl")
public class ErmMysqlDDLGeneratorMojo extends AbstractGeneratorMojo {


    /**
     * 表结构字段修改输出文件
     */
    @Parameter(property = "erm.generator.modifyColumnSqlFileName")
    private String modifyColumnSqlFileName;

    /**
     * 表索引修改输出文件
     */
    @Parameter(property = "erm.generator.modifyIndexSqlFileName")
    private String modifyIndexSqlFileName;

    /**
     * 表修改输出文件
     */
    @Parameter(property = "erm.generator.modifyTableSqlFileName")
    private String modifyTableSqlFileName;


    public ErmMysqlDDLGeneratorMojo() {
    }

    @Override
    protected String generatorName() {
        return "erm db generator";
    }

    @Override
    protected void doExecute() {
        ErmCmpDDLGenerator ermCmpDDLGenerator = new ErmCmpDDLGenerator(env);
        Optional.ofNullable(modifyColumnSqlFileName).ifPresent(ermCmpDDLGenerator::setModifyColumnSqlFileName);
        Optional.ofNullable(modifyIndexSqlFileName).ifPresent(ermCmpDDLGenerator::setModifyIndexSqlFileName);
        Optional.ofNullable(modifyTableSqlFileName).ifPresent(ermCmpDDLGenerator::setModifyTableSqlFileName);
        ermCmpDDLGenerator.exec();
    }


}
