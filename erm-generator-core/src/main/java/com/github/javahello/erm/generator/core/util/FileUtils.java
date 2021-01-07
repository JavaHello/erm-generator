package com.github.javahello.erm.generator.core.util;

import java.util.Optional;

public abstract class FileUtils {

    public static Optional<String> fileExtra(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }

    public static String fileName(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(0, filename.lastIndexOf(".")))
                .orElse(filename);
    }

    public static boolean isExtra(String filename, String ext) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1))
                .filter(ext::equals).isPresent();
    }
}
