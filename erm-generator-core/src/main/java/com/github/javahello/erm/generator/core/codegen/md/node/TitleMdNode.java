package com.github.javahello.erm.generator.core.codegen.md.node;

import com.github.javahello.erm.generator.core.codegen.md.MdTitleLevel;
import com.github.javahello.erm.generator.core.codegen.md.MdType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TitleMdNode extends AbstractMdNode {


    private MdTitleLevel level;

    private List<MdNode> subNodes = new ArrayList<>();

    public TitleMdNode(MdTitleLevel level) {
        this.level = level;
    }

    public TitleMdNode(MdTitleLevel level, String text) {
        this.level = level;
        this.addText(text);
    }

    @Override
    public MdType type() {
        return MdType.TITLE;
    }

    @Override
    public <T extends MdNode> Optional<List<T>> selectSubs() {
        @SuppressWarnings("unchecked")
        List<T> r = (List<T>) subNodes;
        return Optional.of(r);
    }

    @Override
    public <T extends MdNode> Optional<T> selectSub() {
        return Optional.empty();
    }

    @Override
    public void addSub(MdNode subNode) {
        subNodes.add(subNode);
    }

    @Override
    public void addText(String text) {
        super.addText(level.covMd(text));
    }
}
