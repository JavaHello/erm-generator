package com.github.javahello.erm.generator.core.codegen.md.node;

import com.github.javahello.erm.generator.core.codegen.md.ICovMd;
import com.github.javahello.erm.generator.core.codegen.md.MdType;

import java.util.List;
import java.util.Optional;

public interface MdNode extends ICovMd {

    MdType type();

    <T extends MdNode> Optional<List<T>> selectSubs();

    <T extends MdNode> Optional<T> selectSub();

    void addSub(MdNode subNode);

    void addText(String text);

}
