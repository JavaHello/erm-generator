package com.github.javahello.erm.generator.core.codegen.md.node;

import com.github.javahello.erm.generator.core.codegen.md.MdTableHead;
import com.github.javahello.erm.generator.core.codegen.md.MdTableToward;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TableMdNodeTest {

    @Test
    public void testMdTable() {
        TableMdNode tableMdNode = new TableMdNode();

        tableMdNode.addHead(new MdTableHead("序号", MdTableToward.L));
        tableMdNode.addHead(new MdTableHead("表明", MdTableToward.C));
        tableMdNode.addHead(new MdTableHead("描述", MdTableToward.R));

        tableMdNode.nextTr();
        tableMdNode.addTd("1");
        tableMdNode.addTd("USER");
        tableMdNode.addTd("用户表");

        tableMdNode.nextTr();
        tableMdNode.addTd("2");
        tableMdNode.addTd("CONFIG");
        tableMdNode.addTd("配置表");

        tableMdNode.nextTr();
        tableMdNode.addTd("3");
        tableMdNode.addTd("ROLE");
        tableMdNode.addTd("角色表");

        assertEquals(
                "序号|表明|描述\n" +
                ":---|:---:|---:\n" +
                "1|USER|用户表\n" +
                "2|CONFIG|配置表\n" +
                "3|ROLE|角色表\n", tableMdNode.toMd());
    }

}