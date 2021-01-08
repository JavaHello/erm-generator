package com.github.javahello.erm.generator.core.util;

import java.util.Optional;

public abstract class FileUtils {

    public static Optional<String> fileExtra(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(f.lastIndexOf(".") + 1));
    }

    public static String fileName(String filename) {
        String result = Optional.ofNullable(filename)
                .filter(e -> e.contains("/"))
                .map(f -> f.substring(f.lastIndexOf("/") + 1))
                .orElse(filename);
        return Optional.ofNullable(result)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(0, f.lastIndexOf(".")))
                .orElse(result);
    }

    public static boolean isExtra(String filename, String ext) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(f.lastIndexOf(".") + 1))
                .filter(ext::equals).isPresent();
    }
}
