package com.github.javahello.erm.generator.core.codegen.md;

import com.github.javahello.erm.generator.core.util.StringHelper;

public enum MdTableToward {
    L(":-"),
    R("-:"),
    C(":-:"),
    ;
    private String code;
    private static final String sp = "-";

    MdTableToward(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public String covMd(int c) {
        String gen = StringHelper.gen(sp, c);
        return code.replace(sp, gen);
    }
}