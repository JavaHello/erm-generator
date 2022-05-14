package com.github.javahello.erm.generator.core.codegen.md;


import com.github.javahello.erm.generator.core.codegen.md.node.MdNode;
import com.github.javahello.erm.generator.core.codegen.md.node.TableMdNode;
import com.github.javahello.erm.generator.core.codegen.md.node.TitleMdNode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MdBuilder implements ICovMd {

    private final List<MdNode> mdNodes = new ArrayList<>();

    public static MdBuilder newBuild() {
        return new MdBuilder();
    }

    private MdBuilder() {
    }

    public TitleMdNode addTitle(MdTitleLevel titleLevel, String text) {
        TitleMdNode titleMdNode = new TitleMdNode(titleLevel, text);
        mdNodes.add(titleMdNode);
        return titleMdNode;
    }

    public TableMdNode addTable() {
        TableMdNode tableMdNode = new TableMdNode();
        mdNodes.add(tableMdNode);
        return tableMdNode;
    }


    @Override
    public String toMd() {
        return mdNodes.stream().map(ICovMd::toMd).collect(Collectors.joining(lf));
    }
}
