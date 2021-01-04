package com.github.javahello.erm.generator.core.internal;

import com.alibaba.fastjson.util.TypeUtils;
import com.github.javahello.erm.generator.core.config.ErmSourceConfiguration;
import com.github.javahello.erm.generator.core.internal.sqltype.SqlType;
import com.github.javahello.erm.generator.core.model.db.Column;
import com.github.javahello.erm.generator.core.model.db.Index;
import com.github.javahello.erm.generator.core.model.db.Table;
import com.github.javahello.erm.generator.core.model.erm.*;
import com.github.javahello.erm.generator.core.util.MapHelper;
import com.github.javahello.erm.generator.core.util.TypeMap;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.mybatis.generator.logging.Log;
import org.mybatis.generator.logging.LogFactory;

import java.io.File;
import java.util.*;

/**
 * @author kaiv2
 */
public class ErmRead implements ErmMetaData {

    private static Log log = LogFactory.getLog(ErmRead.class);


    public ErmRead(ErmSourceConfiguration ermSourceConfiguration) {
        this(ermSourceConfiguration.getErmSources());
    }

    public ErmRead(List<String> fileList) {
        this.fileList = fileList;
        read();
    }

    private List<String> fileList;

    private List<ErmDiagram> ermList = new ArrayList<>();

    private List<Table> tables = new ArrayList<>();
    private Map<String, Table> tableMap = new HashMap<>();

    public void read() {

        for (String ermFile : fileList) {
            try {
                SAXReader sr = new SAXReader();
                Document doc = sr.read(new File(ermFile));
                Element rootElement = doc.getRootElement();

                ErmDiagram ermDiagram = new ErmDiagram();
                ermDiagram.setFileName(ermFile);

                ermDiagram.setSettings(readSettings(rootElement));
                ermDiagram.setWordList(readWordList(rootElement));
                ermDiagram.setTables(readTables(rootElement));
                ermList.add(ermDiagram);
            } catch (DocumentException e) {
                log.warn(String.format("解析erm %s 文件失败:%s", ermFile, e.getMessage()));
                e.printStackTrace();
            }
        }
        covTable();
    }

    private List<ErmTable> readTables(Element rootElement) {
        List<ErmTable> ermTableList = new ArrayList<>();
        List<Node> xmlTables = rootElement.selectNodes("/diagram/contents/table");
        for (Node xmlTable : xmlTables) {
            ErmTable ermTable = new ErmTable();

            ermTable.setId(xmlTable.selectSingleNode("id").getStringValue());
            ermTable.setPhysicalName(xmlTable.selectSingleNode("physical_name").getStringValue());
            ermTable.setLogicalName(xmlTable.selectSingleNode("logical_name").getStringValue());
            ermTable.setDescription(xmlTable.selectSingleNode("description").getStringValue());

            ermTable.setColumns(readColumns(xmlTable));
            ermTable.setIndexes(readIndexes(xmlTable));

            ermTableList.add(ermTable);
        }
        return ermTableList;
    }

    private List<ErmIndex> readIndexes(Node xmlTable) {
        List<ErmIndex> ermIndexList = new ArrayList<>();
        List<Node> xmlIndexList = xmlTable.selectNodes("indexes/inidex");
        for (Node xmlIndex : xmlIndexList) {
            ErmIndex ermIndex = new ErmIndex();
            ermIndex.setFullText(xmlIndex.selectSingleNode("full_text").getStringValue());
            String nonUnique = xmlIndex.selectSingleNode("non_unique").getStringValue();
            ermIndex.setNonUnique(TypeUtils.castToBoolean(nonUnique));
            ermIndex.setName(xmlIndex.selectSingleNode("name").getStringValue());
            ermIndex.setType(xmlIndex.selectSingleNode("type").getStringValue());
            ermIndex.setDescription(xmlIndex.selectSingleNode("description").getStringValue());
            ermIndex.setColumns(readIndexColumns(xmlIndex));
            ermIndexList.add(ermIndex);
        }
        return ermIndexList;
    }

    private List<ErmIndex.Column> readIndexColumns(Node xmlIndex) {
        List<ErmIndex.Column> indexColumns = new ArrayList<>();
        List<Node> xmlColumns = xmlIndex.selectNodes("columns/column");
        for (Node xmlColumn : xmlColumns) {
            ErmIndex.Column column = new ErmIndex.Column();
            column.setId(xmlColumn.selectSingleNode("id").getStringValue());
            column.setDesc(xmlColumn.selectSingleNode("desc").getStringValue());
            indexColumns.add(column);
        }
        return indexColumns;
    }

    private List<ErmColumn> readColumns(Node xmlTable) {
        List<ErmColumn> ermColumnList = new ArrayList<>();
        List<Node> xmlColumns = xmlTable.selectNodes("columns/normal_column");
        for (Node xmlColumn : xmlColumns) {
            ErmColumn ermColumn = new ErmColumn();
            ermColumn.setId(xmlColumn.selectSingleNode("id").getStringValue());
            ermColumn.setWordId(xmlColumn.selectSingleNode("word_id").getStringValue());
            ermColumn.setDescription(xmlColumn.selectSingleNode("description").getStringValue());
            ermColumn.setUniqueKeyName(xmlColumn.selectSingleNode("unique_key_name").getStringValue());
            ermColumn.setLogicalName(xmlColumn.selectSingleNode("logical_name").getStringValue());
            ermColumn.setPhysicalName(xmlColumn.selectSingleNode("physical_name").getStringValue());
            ermColumn.setType(xmlColumn.selectSingleNode("type").getStringValue());
            ermColumn.setConstraint(xmlColumn.selectSingleNode("constraint").getStringValue());
            ermColumn.setDefaultValue(xmlColumn.selectSingleNode("default_value").getStringValue());
            String autoIncrement = xmlColumn.selectSingleNode("auto_increment").getStringValue();
            ermColumn.setAutoIncrement(TypeUtils.castToBoolean(autoIncrement));
            ermColumn.setForeignKey(xmlColumn.selectSingleNode("foreign_key").getStringValue());
            String notNull = xmlColumn.selectSingleNode("not_null").getStringValue();
            ermColumn.setNotNull(TypeUtils.castToBoolean(notNull));
            String primaryKey = xmlColumn.selectSingleNode("primary_key").getStringValue();
            ermColumn.setPrimaryKey(TypeUtils.castToBoolean(primaryKey));
            String uniqueKey = xmlColumn.selectSingleNode("unique_key").getStringValue();
            ermColumn.setUniqueKey(TypeUtils.castToBoolean(uniqueKey));
            ermColumn.setCharacterSet(xmlColumn.selectSingleNode("character_set").getStringValue());
            ermColumn.setCollation(xmlColumn.selectSingleNode("collation").getStringValue());
            ermColumnList.add(ermColumn);
        }
        return ermColumnList;
    }

    private List<ErmWord> readWordList(Element rootElement) {
        List<ErmWord> ermWordList = new ArrayList<>();
        List<Node> wordList = rootElement.selectNodes("/diagram/dictionary/word");
        for (Node word : wordList) {
            ErmWord ermWord = new ErmWord();
            ermWord.setId(word.selectSingleNode("id").getStringValue());
            String length = word.selectSingleNode("length").getStringValue();
            ermWord.setLength(TypeUtils.castToInt(length));
            String decimal = word.selectSingleNode("decimal").getStringValue();
            ermWord.setDecimal(TypeUtils.castToInt(decimal));
            ermWord.setArray(word.selectSingleNode("array").getStringValue());
            ermWord.setArrayDimension(word.selectSingleNode("array_dimension").getStringValue());
            ermWord.setUnsigned(word.selectSingleNode("unsigned").getStringValue());
            ermWord.setZerofill(word.selectSingleNode("zerofill").getStringValue());
            ermWord.setBinary(word.selectSingleNode("binary").getStringValue());
            ermWord.setArgs(word.selectSingleNode("args").getStringValue());
            ermWord.setCharSemantics(word.selectSingleNode("char_semantics").getStringValue());
            ermWord.setDescription(word.selectSingleNode("description").getStringValue());
            ermWord.setLogicalName(word.selectSingleNode("logical_name").getStringValue());
            ermWord.setPhysicalName(word.selectSingleNode("physical_name").getStringValue());
            ermWord.setType(word.selectSingleNode("type").getStringValue());
            ermWordList.add(ermWord);
        }
        return ermWordList;
    }

    private ErmSetting readSettings(Element rootElement) {
        Node settings = rootElement.selectSingleNode("/diagram/settings");
        ErmSetting ermSetting = new ErmSetting();
        ermSetting.setDatabase(settings.selectSingleNode("database").getStringValue());
        return ermSetting;
    }

    private void covTable() {
        for (ErmDiagram ermDiagram : ermList) {
            String database = ermDiagram.getSettings().getDatabase();
            Map<String, ErmWord> wordMap = MapHelper.uniqueGroup(ermDiagram.getWordList(), ErmWord::getId);
            List<ErmTable> ermTables = ermDiagram.getTables();
            for (ErmTable ermTable : ermTables) {
                String tableName = ermTable.getPhysicalName();
                if (tableMap.containsKey(tableName)) {
                    log.warn(tableName + " 表重复，不再处理!!!");
                    continue;
                }
                Table table = new Table();
                table.setTableName(tableName);
                table.setTableComment(ermTable.getLogicalName());
                List<Column> pks = new ArrayList<>();
                List<Index> ids = new ArrayList<>();
                List<Column> cols = new ArrayList<>();

                List<ErmColumn> ermCols = ermTable.getColumns();
                Map<String, Column> colMap = new HashMap<>();
                for (ErmColumn ermColumn : ermCols) {
                    Column col = new Column();
                    ErmWord ermWord = wordMap.get(ermColumn.getWordId());
                    if (ermWord == null) {
                        continue;
                    }
                    col.setColumnName(ermWord.getPhysicalName());
                    col.setColumnComment(ermWord.getLogicalName());
                    String columnType = ermWord.getType();
                    SqlType sqlType = SqlType.valueOfOrId(database, columnType);

                    columnType = Optional.ofNullable(sqlType).map(e -> e.getAlias(database)).orElse(columnType);
                    int endIndex = columnType.indexOf('(');
                    if (endIndex != -1) {
                        columnType = columnType.substring(0, endIndex);
                    }
                    col.setColumnType(columnType);
                    col.setJdbcType(TypeMap.jdbcType(columnType));
                    col.setDefaultValue(ermColumn.getDefaultValue());
                    col.setAutoIncrement(ermColumn.getAutoIncrement());
                    col.setLength(ermWord.getLength());
                    col.setDecimal(ermWord.getDecimal());
                    col.setUniqueKey(ermColumn.getUniqueKey());
                    col.setPrimaryKey(ermColumn.getPrimaryKey());
                    col.setNotNull(ermColumn.getNotNull());

                    if (col.isPrimaryKey()) {
                        pks.add(col);
                    }
                    cols.add(col);

                    if (col.isUniqueKey()) {
                        ids.add(genIndex(col));
                    }
                    colMap.put(ermColumn.getId(), col);
                }

                List<ErmIndex> ermIndexes = ermTable.getIndexes();

                for (ErmIndex ermIndex : ermIndexes) {
                    Index index = new Index();
                    index.setIndexName(ermIndex.getName());
                    index.setNonUnique(ermIndex.getNonUnique());
                    List<Column> idxCols = new ArrayList<>();
                    List<com.github.javahello.erm.generator.core.model.erm.ErmIndex.Column> ermIdxCols = ermIndex
                            .getColumns();
                    for (com.github.javahello.erm.generator.core.model.erm.ErmIndex.Column ermIdxCol : ermIdxCols) {
                        idxCols.add(colMap.get(ermIdxCol.getId()));
                    }
                    index.setColumns(idxCols);
                    ids.add(index);
                }

                table.setColumns(cols);
                table.setPrimaryKeys(pks);
                table.setIndexes(ids);
                tables.add(table);
                tableMap.put(tableName, table);
            }
        }
    }

    private Index genIndex(Column col) {
        Index idx = new Index();
        idx.setNonUnique(!col.isUniqueKey());
        idx.setIndexName(col.getColumnName());
        idx.setColumns(Arrays.asList(col));
        return idx;
    }

    public List<String> getFileList() {
        return fileList;
    }

    public List<ErmDiagram> getErmList() {
        return ermList;
    }

    public void setErmList(List<ErmDiagram> ermList) {
        this.ermList = ermList;
    }

    @Override
    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }

    @Override
    public Optional<List<Column>> getPrimaryKeys(String table) {
        return Optional.ofNullable(tableMap.get(table)).map(Table::getPrimaryKeys);
    }

    @Override
    public Optional<List<Column>> getColumns(String table) {
        return Optional.ofNullable(tableMap.get(table)).map(Table::getColumns);
    }

    @Override
    public Optional<Table> getTable(String table) {
        return Optional.ofNullable(tableMap.get(table));
    }

}
