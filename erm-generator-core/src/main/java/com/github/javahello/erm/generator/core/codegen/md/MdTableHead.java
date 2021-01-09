package com.github.javahello.erm.generator.core.codegen.md;

public class MdTableHead {
    private String name;
    private MdTableToward toward;

    public MdTableHead(String name, MdTableToward toward) {
        this.name = name;
        this.toward = toward;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MdTableToward getToward() {
        return toward;
    }

    public void setToward(MdTableToward toward) {
        this.toward = toward;
    }

}