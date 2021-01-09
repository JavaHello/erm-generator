package com.github.javahello.erm.generator.core.codegen.md;

public enum MdTitleLevel {
    L1("#"),
    L2("##"),
    L3("###"),
    ;
    private String code;

    MdTitleLevel(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public String covMd(String title) {
        return this.code + " " + title;
    }

}