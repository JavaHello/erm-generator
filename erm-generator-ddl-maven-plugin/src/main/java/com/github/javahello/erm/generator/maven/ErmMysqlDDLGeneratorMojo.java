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
import com.github.javahello.erm.generator.core.model.ErmDDLEnv;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 使用 generate-mysql-ddl 生成 erm 差异 SQL.
 */
@Mojo(name = "generate-mysql-ddl")
public class ErmMysqlDDLGeneratorMojo extends AbstractMojo {

    /**
     * 环境变量获取
     */
    ErmDDLEnv env = new ErmDDLEnv();

    /**
     * Maven Project.
     */
    @Parameter(property = "project", required = true, readonly = true)
    private MavenProject project;

    /**
     * Output Directory.
     */
    @Parameter(property = "erm.generator.outputDirectory",
            defaultValue = "${project.basedir}/.dev-out/", required = true)
    private File outputDirectory;

    /**
     * new Erm Files.
     */
    @Parameter(property = "erm.generator.newErmFiles", required = true)
    private List<File> newErmFiles;


    /**
     * old Erm Files.
     */
    @Parameter(property = "erm.generator.oldErmFiles", required = false)
    private List<File> oldErmFiles;


    /**
     * Skip generator.
     */
    @Parameter(property = "erm.generator.skip", defaultValue = "false")
    private boolean skip;

    public ErmMysqlDDLGeneratorMojo() {
    }


    @Override
    public void execute() throws MojoExecutionException {
        if (skip) {
            getLog().info("erm db generator is skipped.");
            return;
        }
        env.setExtraProperties(project.getProperties());
        env.setNewErmList(newErmFiles.stream().map(File::getAbsolutePath).collect(Collectors.toList()));
        Optional.ofNullable(oldErmFiles).ifPresent(oldErmFiles -> {
            env.setOldErmList(oldErmFiles.stream().map(File::getAbsolutePath).collect(Collectors.toList()));
        });
        ErmCmpDDLGenerator ermCmpDDLGenerator = new ErmCmpDDLGenerator(env) {

            @Override
            protected void afterExec() {
                super.afterExec();
                File allSqlFile = new File(outputDirectory.getAbsolutePath() + "/all.sql");
                try {
                    if (allSqlFile.exists() || allSqlFile.createNewFile()) {
                        Files.write(allSqlFile.toPath(), allSql.getBytes(StandardCharsets.UTF_8));
                    } else {
                        getLog().error(allSqlFile.getAbsolutePath() + ", 文件创建失败");
                    }
                } catch (IOException e) {
                    getLog().error("写入SQL到文件失败", e);
                }
            }
        };
        ermCmpDDLGenerator.exec();
    }


}
