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

import com.github.javahello.erm.generator.core.api.ErmMdGenerator;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.util.Optional;

/**
 * 使用 generate-markdown 生成 markdown 格式表清单.
 */
@Mojo(name = "generate-markdown")
public class ErmMdGeneratorMojo extends AbstractGeneratorMojo {


    /**
     * 表清单 markdown 输出文件
     */
    @Parameter(property = "erm.generator.outMdFileName")
    private String outExcelFileName;


    public ErmMdGeneratorMojo() {
    }

    @Override
    protected String generatorName() {
        return "markdown generator";
    }

    @Override
    protected void doExecute() {
        ErmMdGenerator ermMdGenerator = new ErmMdGenerator(env);
        Optional.ofNullable(outExcelFileName).ifPresent(ermMdGenerator::setOutFileName);
        ermMdGenerator.exec();
    }


}
