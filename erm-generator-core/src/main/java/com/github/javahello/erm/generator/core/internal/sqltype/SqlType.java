package com.github.javahello.erm.generator.core.internal.sqltype;

import com.github.javahello.erm.generator.core.codegen.ddl.DbType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SqlType implements Serializable {

	private static Logger logger = Logger.getLogger(SqlType.class.getName());

	private static final long serialVersionUID = -8273043043893517634L;

	public static final String SQL_TYPE_ID_SERIAL = "serial";

	public static final String SQL_TYPE_ID_BIG_SERIAL = "bigserial";

	public static final String SQL_TYPE_ID_INTEGER = "integer";

	public static final String SQL_TYPE_ID_BIG_INT = "bigint";

	public static final String SQL_TYPE_ID_CHAR = "character";

	public static final String SQL_TYPE_ID_VARCHAR = "varchar";

	private static final Pattern NEED_LENGTH_PATTERN = Pattern
			.compile(".+\\([a-zA-Z][,\\)].*");

	private static final Pattern NEED_DECIMAL_PATTERN1 = Pattern
			.compile(".+\\([a-zA-Z],[a-zA-Z]\\)");

	private static final Pattern NEED_DECIMAL_PATTERN2 = Pattern
			.compile(".+\\([a-zA-Z]\\).*\\([a-zA-Z]\\)");

	private static final List<SqlType> SQL_TYPE_LIST = new ArrayList<SqlType>();

	private String name;

	private Class javaClass;

	private boolean needArgs;

	boolean fullTextIndexable;

	private static Map<String, Map<TypeKey, SqlType>> dbSqlTypeMap = new HashMap<String, Map<TypeKey, SqlType>>();

	private static Map<String, Map<SqlType, String>> dbSqlTypeToAliasMap = new HashMap<String, Map<SqlType, String>>();

	private static Map<String, Map<String, SqlType>> dbAliasToSqlTypeMap = new HashMap<String, Map<String, SqlType>>();

	static {
		try {
			SqlTypeFactory.load();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}
	}

	public static class TypeKey {
		private String alias;

		private int size;

		private int decimal;

		public TypeKey(String alias, int size, int decimal) {
			if (alias != null) {
				alias = alias.toUpperCase();
			}

			this.alias = alias;

			if (size == Integer.MAX_VALUE) {
				this.size = 0;
			} else {
				this.size = size;
			}

			this.decimal = decimal;
		}

		@Override
		public boolean equals(Object obj) {
			TypeKey other = (TypeKey) obj;

			if (this.alias == null) {
				if (other.alias == null) {
					if (this.size == other.size
							&& this.decimal == other.decimal) {
						return true;
					}
					return false;

				} else {
					return false;
				}

			} else {
				if (this.alias.equals(other.alias) && this.size == other.size
						&& this.decimal == other.decimal) {
					return true;
				}
			}

			return false;
		}

		@Override
		public int hashCode() {
			if (this.alias == null) {
				return (this.size * 10) + this.decimal;
			}
			return (this.alias.hashCode() * 100) + (this.size * 10)
					+ this.decimal;
		}

		@Override
		public String toString() {
			return "TypeKey [alias=" + alias + ", size=" + size + ", decimal="
					+ decimal + "]";
		}

	}

	public SqlType(String name, Class javaClass, boolean needArgs,
			boolean fullTextIndexable) {
		this.name = name;
		this.javaClass = javaClass;
		this.needArgs = needArgs;
		this.fullTextIndexable = fullTextIndexable;

		SQL_TYPE_LIST.add(this);
	}

	public static void setDBAliasMap(
			Map<String, Map<SqlType, String>> dbSqlTypeToAliasMap,
			Map<String, Map<String, SqlType>> dbAliasToSqlTypeMap,
			Map<String, Map<TypeKey, SqlType>> dbSqlTypeMap) {
		SqlType.dbSqlTypeMap = dbSqlTypeMap;
		SqlType.dbSqlTypeToAliasMap = dbSqlTypeToAliasMap;
		SqlType.dbAliasToSqlTypeMap = dbAliasToSqlTypeMap;
	}

	public void addToSqlTypeMap(TypeKey typeKey, String database) {
		Map<TypeKey, SqlType> sqlTypeMap = dbSqlTypeMap.get(database);
		sqlTypeMap.put(typeKey, this);
	}

	public String getId() {
		return this.name;
	}

	public Class getJavaClass() {
		return this.javaClass;
	}

	public boolean doesNeedArgs() {
		return this.needArgs;
	}

	public boolean isFullTextIndexable() {
		return this.fullTextIndexable;
	}

	protected static List<SqlType> getAllSqlType() {
		return SQL_TYPE_LIST;
	}

	public static SqlType valueOf(String database, String alias) {
		return dbAliasToSqlTypeMap.get(database).get(alias);
	}

	public static SqlType valueOfOrId(String database, String alias) {
		return Optional.ofNullable(dbAliasToSqlTypeMap.get(database).get(alias)).orElseGet(() -> valueOfId(alias));
	}


	public static SqlType valueOf(String database, String alias, int size,
			int decimal) {
		if (alias == null) {
			return null;
		}

		Map<TypeKey, SqlType> sqlTypeMap = dbSqlTypeMap.get(database);

		TypeKey typeKey = new TypeKey(alias, size, decimal);
		SqlType sqlType = sqlTypeMap.get(typeKey);

		if (sqlType != null) {
			return sqlType;
		}

		if (decimal > 0) {
			decimal = -1;

			typeKey = new TypeKey(alias, size, decimal);
			sqlType = sqlTypeMap.get(typeKey);

			if (sqlType != null) {
				return sqlType;
			}
		}

		if (size > 0) {
			size = -1;

			typeKey = new TypeKey(alias, size, decimal);
			sqlType = sqlTypeMap.get(typeKey);

			if (sqlType != null) {
				return sqlType;
			}
		}

		typeKey = new TypeKey(alias, 0, 0);
		sqlType = sqlTypeMap.get(typeKey);

		return sqlType;
	}

	public static SqlType valueOfId(String id) {
		SqlType sqlType = null;

		if (id == null) {
			return null;
		}

		for (SqlType type : SQL_TYPE_LIST) {
			if (id.equals(type.getId())) {
				sqlType = type;
			}
		}
		return sqlType;
	}

	public boolean isNeedLength(String database) {
		String alias = this.getAlias(database);
		if (alias == null) {
			return false;
		}

		Matcher matcher = NEED_LENGTH_PATTERN.matcher(alias);

		if (matcher.matches()) {
			return true;
		}

		return false;
	}

	public boolean isNeedDecimal(String database) {
		String alias = this.getAlias(database);
		if (alias == null) {
			return false;
		}

		Matcher matcher = NEED_DECIMAL_PATTERN1.matcher(alias);

		if (matcher.matches()) {
			return true;
		}

		matcher = NEED_DECIMAL_PATTERN2.matcher(alias);

		if (matcher.matches()) {
			return true;
		}

		return false;
	}

	public boolean isNeedCharSemantics(String database) {
		if (!DbType.ORACLE.getCode().equals(database)) {
			return false;
		}

		if (this.name.startsWith(SQL_TYPE_ID_CHAR)
				|| this.name.startsWith(SQL_TYPE_ID_VARCHAR)) {
			return true;
		}

		return false;
	}

	public boolean isTimestamp() {
		if (this.javaClass == Date.class) {
			return true;
		}

		return false;
	}

	public boolean isNumber() {
		if (Number.class.isAssignableFrom(this.javaClass)) {
			return true;
		}

		return false;
	}

	public static List<String> getAliasList(String database) {
		Map<SqlType, String> aliasMap = dbSqlTypeToAliasMap.get(database);

		Set<String> aliases = new LinkedHashSet<String>();

		for (Entry<SqlType, String> entry : aliasMap.entrySet()) {
			String alias = entry.getValue();
			aliases.add(alias);
		}

		List<String> list = new ArrayList<String>(aliases);

		Collections.sort(list);

		return list;
	}

	public String getAlias(String database) {
		Map<SqlType, String> aliasMap = dbSqlTypeToAliasMap.get(database);

		return aliasMap.get(this);
	}

	public boolean isUnsupported(String database) {
		String alias = this.getAlias(database);

		if (alias == null) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof SqlType)) {
			return false;
		}

		SqlType type = (SqlType) obj;

		return this.name.equals(type.name);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return this.getId();
	}




}
