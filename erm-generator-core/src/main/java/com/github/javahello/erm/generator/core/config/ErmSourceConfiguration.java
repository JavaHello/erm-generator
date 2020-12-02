package com.github.javahello.erm.generator.core.config;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.config.PropertyHolder;

/**
 * @author kaiv2
 */
public class ErmSourceConfiguration extends PropertyHolder {

    private String ermPath;

    private List<String> ermSources;


    public ErmSourceConfiguration() {
        ermSources = new ArrayList<>();
    }


    public void validate(List<String> errors) {

        
        if (ermSources.isEmpty()) { //$NON-NLS-1$
            errors.add("ermSource is empty"); //$NON-NLS-1$
        }
    }

    public void addErmFile(String ermfile) {
        this.ermSources.add(ermfile);
    }

    public List<String> getErmSources() {
        return ermSources;
    }

    public void setErmSources(List<String> ermSources) {
        this.ermSources = ermSources;
    }


    public String getErmPath() {
        return ermPath;
    }

    public void setErmPath(String ermPath) {
        this.ermPath = ermPath;
    }
}
