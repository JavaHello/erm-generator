package com.github.javahello.erm.generator.core.codegen.md.node;

import com.github.javahello.erm.generator.core.codegen.md.MdType;

import java.util.List;
import java.util.Optional;

public class TextMdNode extends AbstractMdNode {

    public TextMdNode() {
    }

    public TextMdNode(String text) {
        super.addText(text);
    }

    @Override
    public MdType type() {
        return MdType.TEXT;
    }

    @Override
    public <T extends MdNode> Optional<List<T>> selectSubs() {
        return Optional.empty();
    }

    @Override
    public <T extends MdNode> Optional<T> selectSub() {
        return Optional.empty();
    }


}
