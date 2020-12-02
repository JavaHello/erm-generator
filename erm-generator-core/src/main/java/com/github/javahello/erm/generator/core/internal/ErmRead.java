package com.github.javahello.erm.generator.core.internal;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.xml.bind.JAXBException;

import com.github.javahello.erm.generator.core.config.ErmSourceConfiguration;
import com.github.javahello.erm.generator.core.model.db.Column;
import com.github.javahello.erm.generator.core.model.db.Index;
import com.github.javahello.erm.generator.core.model.db.Table;
import com.github.javahello.erm.generator.core.model.erm.ErmColumn;
import com.github.javahello.erm.generator.core.model.erm.ErmDiagram;
import com.github.javahello.erm.generator.core.model.erm.ErmIndex;
import com.github.javahello.erm.generator.core.model.erm.ErmTable;
import com.github.javahello.erm.generator.core.model.erm.ErmWord;
import com.github.javahello.erm.generator.core.util.MapHelper;
import com.github.javahello.erm.generator.core.util.TypeMap;
import com.github.javahello.erm.generator.core.util.XMLUtil;

import org.mybatis.generator.logging.Log;
import org.mybatis.generator.logging.LogFactory;

/**
 * 
 * @author kaiv2
 *
 */
public class ErmRead implements ErmMetaData {

	private Log log = LogFactory.getLog(getClass());

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

	public void read() {

		for (String ermFile : fileList) {
			try {
				ErmDiagram ermObject = XMLUtil.convertXMLFileToObject(ErmDiagram.class, ermFile);
				ermList.add(ermObject);
			} catch (FileNotFoundException e) {
				log.warn(String.format("读取erm %s 文件失败:%s", ermFile, e.getMessage()));
				e.printStackTrace();
			} catch (JAXBException e) {
				log.warn(String.format("解析erm %s 文件失败:%s", ermFile, e.getMessage()));
				e.printStackTrace();
			}
		}
		covDb();
	}

	private void covDb() {
		for (ErmDiagram ermDiagram : ermList) {
			Map<String, ErmWord> wordMap = MapHelper.uniqueGroup(ermDiagram.getWordList(), ErmWord::getId);
			List<ErmTable> ermTables = ermDiagram.getTables();
			for (ErmTable ermTable : ermTables) {
				Table table = new Table();
				table.setTablesName(ermTable.getPhysicalName());
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
				table.setIndexs(ids);
				tables.add(table);
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

	public List<Table> getTables() {
		return tables;
	}

	public void setTables(List<Table> tables) {
		this.tables = tables;
	}

	@Override
	public Optional<List<Column>> getPrimaryKeys(String table) {
		return tables.stream().filter(e -> e.getTablesName().equals(table)).map(Table::getPrimaryKeys).findAny();
	}

	@Override
	public Optional<List<Column>> getColumns(String table) {
		return tables.stream().filter(e -> e.getTablesName().equals(table)).map(Table::getColumns).findAny();
	}

	@Override
	public Optional<Table> getTable(String table) {
		return tables.stream().filter(e -> e.getTablesName().equals(table)).findAny();
	}

}
