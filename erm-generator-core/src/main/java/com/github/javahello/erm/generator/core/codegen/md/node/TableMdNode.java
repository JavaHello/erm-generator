package com.github.javahello.erm.generator.core.codegen.md.node;

import com.github.javahello.erm.generator.core.codegen.md.MdTableHead;
import com.github.javahello.erm.generator.core.codegen.md.MdType;
import com.github.javahello.erm.generator.core.util.StringHelper;

import java.util.*;
import java.util.stream.Collectors;

public class TableMdNode extends AbstractMdNode {

    List<MdTableHead> mdTableHeadList = new ArrayList<>();
    List<List<String>> tableContent = new LinkedList<>();

    List<String> currentTd;

    private boolean buildFlag = false;
    private final String sp = "|";

    @Override
    public MdType type() {
        return MdType.TABLE;
    }

    @Override
    public <T extends MdNode> Optional<List<T>> selectSubs() {
        return Optional.empty();
    }

    @Override
    public <T extends MdNode> Optional<T> selectSub() {
        return Optional.empty();
    }

    public TableMdNode addHead(MdTableHead mdTableHead) {
        mdTableHeadList.add(mdTableHead);
        return this;
    }

    private List<String> getCurrentTd() {
        if (tableContent.isEmpty()) {
            currentTd = new LinkedList<>();
            tableContent.add(currentTd);
        }
        return currentTd;
    }

    private List<String> nextCurrentTd() {
        currentTd = new LinkedList<>();
        tableContent.add(currentTd);
        return currentTd;
    }

    public TableMdNode addTd(String text) {
        getCurrentTd().add(Optional.ofNullable(text).orElse(" "));
        return this;
    }

    public TableMdNode addTd(Object text) {
        addTd(Optional.ofNullable(text)
                .map(e -> {
                    if (e instanceof Boolean) {
                        return (Boolean) e ? "*" : null;
                    }
                    return e;
                })
                .map(Objects::toString).orElse(" "));
        return this;
    }

    public TableMdNode nextTr() {
        nextCurrentTd();
        return this;
    }

    @Override
    public String toMd() {
        buildMd();
        return content.toString();
    }

    public void buildMd() {
        if (!buildFlag) {
            buildFlag = true;
            // build
            List<String> headF = new LinkedList<>();
            String title = mdTableHeadList.stream().map(h -> {
                String name = h.getName();
                headF.add(h.getToward().covMd(StringHelper.showLen(name)));
                return name;
            }).collect(Collectors.joining(sp));
            content.append(title).append(lf);
            content.append(String.join(sp, headF)).append(lf);
            String body = tableContent.stream().map(tr -> String.join(sp, tr)).collect(Collectors.joining(lf));
            content.append(body).append(lf);
        }
    }

    public void addHeads(MdTableHead[] tableHeads) {
        for (MdTableHead tableHead : tableHeads) {
            addHead(tableHead);
        }
    }

}
