package com.github.javahello.erm.generator.core.util;

import java.util.List;

/**
 * 比对工具
 */
public abstract class DiffHelper {
    public static String or(String a, String b) {
        return a != null ? a : b;
    }

    public static <T> boolean eqSize(List<T> l1, List<T> l2) {
        return l1 == l2 || (l1 != null && l1.size() == l2.size());
    }

    public static <T> boolean isEmpty(List<T> l1) {
        return l1 == null || l1.isEmpty();
    }

    public static  boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean isNotEmpty(String s) {
        return !isEmpty(s);
    }
}
