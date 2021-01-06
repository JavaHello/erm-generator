package com.github.javahello.erm.generator.maven;

import com.github.javahello.erm.generator.core.model.ErmDiffEnv;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class AbstractGeneratorMojo extends AbstractMojo {

    /**
     * 环境变量获取
     */
    ErmDiffEnv env = new ErmDiffEnv();

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

    @Override
    public void execute() throws MojoExecutionException {
        if (skip) {
            getLog().info(generatorName() + " is skipped.");
            return;
        }
        env.setExtraProperties(project.getProperties());
        env.setNewErmList(newErmFiles.stream().map(File::getAbsolutePath).collect(Collectors.toList()));
        Optional.ofNullable(oldErmFiles).ifPresent(oldErmFiles -> env.setOldErmList(oldErmFiles.stream()
                .map(File::getAbsolutePath).collect(Collectors.toList())));
        doExecute();
    }

    protected abstract String generatorName();

    protected abstract void doExecute();

    public ErmDiffEnv getEnv() {
        return env;
    }

    public void setEnv(ErmDiffEnv env) {
        this.env = env;
    }

    public MavenProject getProject() {
        return project;
    }

    public void setProject(MavenProject project) {
        this.project = project;
    }

    public File getOutputDirectory() {
        return outputDirectory;
    }

    public void setOutputDirectory(File outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    public List<File> getNewErmFiles() {
        return newErmFiles;
    }

    public void setNewErmFiles(List<File> newErmFiles) {
        this.newErmFiles = newErmFiles;
    }

    public List<File> getOldErmFiles() {
        return oldErmFiles;
    }

    public void setOldErmFiles(List<File> oldErmFiles) {
        this.oldErmFiles = oldErmFiles;
    }

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }
}
