package com.github.javahello.erm.generator.core;

import java.util.Objects;

/**
 * 
 * @author kaiv2
 *
 */
public abstract class TestFileHelper {
    
    public static String cpFilePath(String fileName) {
        return Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource(fileName)).getPath();
    }

}
