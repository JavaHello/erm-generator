package com.github.javahello.erm.generator.core.codegen.md.node;

import com.github.javahello.erm.generator.core.codegen.md.MdTitleLevel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TitleMdNodeTest {

    @Test
    public void testMdTitle() {
        TitleMdNode titleMdNode = new TitleMdNode(MdTitleLevel.L1, "第一章");
        titleMdNode.addSub(new TextMdNode("Hello 你好呀"));
        Assertions.assertEquals(
                "# 第一章\n" +
                "Hello 你好呀\n",
                titleMdNode.toMd());
        System.out.println(titleMdNode.toMd());
    }
}