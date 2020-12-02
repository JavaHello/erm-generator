package com.github.javahello.erm.generator.core.util;

import java.io.FileNotFoundException;
import java.util.Optional;

/**
 * @author kaiv2
 */
public abstract class ClasspathFileUtil {

    public static final String CLASSPATH = "classpath:";
    
    public static String cpFilePath(String fileName) throws FileNotFoundException {
        return Optional.ofNullable(Thread.currentThread().getContextClassLoader().getResource(fileName)).orElseThrow(() -> new FileNotFoundException(fileName)).getFile();
    }

	
}
