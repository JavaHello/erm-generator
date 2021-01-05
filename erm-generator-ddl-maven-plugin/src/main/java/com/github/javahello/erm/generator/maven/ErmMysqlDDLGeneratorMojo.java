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
import com.github.javahello.erm.generator.core.codegen.ddl.BaseOutDDL;
import com.github.javahello.erm.generator.core.codegen.ddl.ICovDDL;
import com.github.javahello.erm.generator.core.model.ErmDDLEnv;
import com.github.javahello.erm.generator.core.util.DiffHelper;
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
    @Parameter(property = "erm.generator.oldErmFiles")
    private List<File> oldErmFiles;


    /**
     * Skip generator.
     */
    @Parameter(property = "erm.generator.skip", defaultValue = "false")
    private boolean skip;

    /**
     * 表结构字段修改输出文件
     */
    @Parameter(property = "erm.generator.modifyColumnSqlFileName", defaultValue = "modify_column.sql")
    private String modifyColumnSqlFileName;

    /**
     * 表索引修改输出文件
     */
    @Parameter(property = "erm.generator.modifyIndexSqlFileName", defaultValue = "modify_index.sql")
    private String modifyIndexSqlFileName;

    /**
     * 表修改输出文件
     */
    @Parameter(property = "erm.generator.modifyTableSqlFileName", defaultValue = "modify_table.sql")
    private String modifyTableSqlFileName;


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
        Optional.ofNullable(oldErmFiles).ifPresent(oldErmFiles -> env.setOldErmList(oldErmFiles.stream()
                .map(File::getAbsolutePath).collect(Collectors.toList())));
        ErmCmpDDLGenerator ermCmpDDLGenerator = new ErmCmpDDLGenerator(env) {

            @Override
            protected void afterExec() {
                super.afterExec();
                Optional.ofNullable(allSql).filter(DiffHelper::isNotEmpty).ifPresent(sql -> writeFile(new File(outputDirectory.getAbsolutePath() + File.separator + "all.sql"),
                        sql.getBytes(StandardCharsets.UTF_8)));

                ICovDDL fix = currentOutDDL.fix();

                Optional.ofNullable(fix).map(ICovDDL::covDDL).filter(DiffHelper::isNotEmpty).ifPresent(sql -> writeFile(new File(outputDirectory.getAbsolutePath() + File.separator + "all_fix.sql"),
                        sql.getBytes(StandardCharsets.UTF_8)));

                Optional.ofNullable(currentOutDDL)
                        .map(BaseOutDDL::getModifyColumnSql)
                        .map(Object::toString)
                        .filter(DiffHelper::isNotEmpty)
                        .ifPresent(ddl -> writeFile(new File(outputDirectory.getAbsolutePath() + File.separator + modifyColumnSqlFileName)
                                , ddl.getBytes(StandardCharsets.UTF_8)));

                Optional.ofNullable(currentOutDDL)
                        .map(BaseOutDDL::getModifyIndexSql)
                        .map(Object::toString)
                        .filter(DiffHelper::isNotEmpty)
                        .ifPresent(ddl -> writeFile(new File(outputDirectory.getAbsolutePath() + File.separator + modifyIndexSqlFileName)
                                , ddl.getBytes(StandardCharsets.UTF_8)));

                Optional.ofNullable(currentOutDDL)
                        .map(BaseOutDDL::getModifyTableSql)
                        .map(Object::toString)
                        .filter(DiffHelper::isNotEmpty)
                        .ifPresent(ddl -> writeFile(new File(outputDirectory.getAbsolutePath() + File.separator + modifyTableSqlFileName)
                                , ddl.getBytes(StandardCharsets.UTF_8)));

                Optional.ofNullable(currentOutDDL)
                        .map(BaseOutDDL::getModifyColumnSqlFix)
                        .map(Object::toString)
                        .filter(DiffHelper::isNotEmpty)
                        .ifPresent(ddl -> writeFile(new File(outputDirectory.getAbsolutePath() + File.separator + fixFileName(modifyColumnSqlFileName))
                                , ddl.getBytes(StandardCharsets.UTF_8)));

                Optional.ofNullable(currentOutDDL)
                        .map(BaseOutDDL::getModifyIndexSqlFix)
                        .map(Object::toString)
                        .filter(DiffHelper::isNotEmpty)
                        .ifPresent(ddl -> writeFile(new File(outputDirectory.getAbsolutePath() + File.separator + fixFileName(modifyIndexSqlFileName))
                                , ddl.getBytes(StandardCharsets.UTF_8)));

                Optional.ofNullable(currentOutDDL)
                        .map(BaseOutDDL::getModifyTableSqlFix)
                        .map(Object::toString)
                        .filter(DiffHelper::isNotEmpty)
                        .ifPresent(ddl -> writeFile(new File(outputDirectory.getAbsolutePath() + File.separator + fixFileName(modifyTableSqlFileName))
                                , ddl.getBytes(StandardCharsets.UTF_8)));
            }

            private String fixFileName(String sqlFileName) {
                String result;
                int i = sqlFileName.indexOf('.');
                if (i > -1) {
                    String filename = sqlFileName.substring(0, i);
                    String ext = sqlFileName.substring(i);
                    result = filename + "_fix" + ext;
                } else {
                    result = sqlFileName + "_fix.sql";
                }
                return result;
            }

            private void writeFile(File allSqlFile, byte[] sqlBytes) {
                try {
                    if (allSqlFile.exists() || allSqlFile.createNewFile()) {
                        Files.write(allSqlFile.toPath(), sqlBytes);
                    } else {
                        getLog().error(allSqlFile.getAbsolutePath() + ", 文件创建失败");
                    }
                } catch (IOException e) {
                    getLog().error(allSqlFile.getAbsolutePath() + "写入SQL到文件失败", e);
                }
            }
        };
        ermCmpDDLGenerator.exec();
    }


}
