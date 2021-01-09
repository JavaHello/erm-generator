package com.github.javahello.erm.generator.core.util;

import java.nio.charset.StandardCharsets;

public interface StringHelper {

    static String gen(String s, int c) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < c; i++) {
            sb.append(s);
        }
        return sb.toString();
    }


    static int showLen(String s) {
        int length = s.length();
        int bl = s.getBytes(StandardCharsets.UTF_8).length;
        if (bl > length) {
            double t = (bl - length) * (1f/3f);
            length += t;
        }
        return length;
    }
}
