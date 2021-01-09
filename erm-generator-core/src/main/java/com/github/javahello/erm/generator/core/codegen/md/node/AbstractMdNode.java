package com.github.javahello.erm.generator.core.codegen.md.node;

import java.util.List;
import java.util.Optional;

public abstract class AbstractMdNode implements MdNode {

    /**
     * 当前节点内容
     */
    protected final StringBuilder content = new StringBuilder();


    @Override
    public void addText(String text) {
        content.append(text);
    }

    @Override
    public String toMd() {
        StringBuilder md = new StringBuilder();
        md.append(content).append(lf);
        Optional<List<MdNode>> mdNodesOpt = selectSubs();
        mdNodesOpt.ifPresent(subs -> {
            for (MdNode sub : subs) {
                md.append(sub.toMd());
            }
        });
        return md.toString();
    }

    @Override
    public void addSub(MdNode subNode) {
        throw new UnsupportedOperationException(type().name() + " 无法 addSub");
    }
}
