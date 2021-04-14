package com.github.javahello.erm.generator.core.codegen.ddl;

import com.github.javahello.erm.generator.core.codegen.ddl.mysql.GenMysqlDDL;
import com.github.javahello.erm.generator.core.internal.TableCache;
import com.github.javahello.erm.generator.core.model.diff.DiffTable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class OutDDLFactory {
    static Map<DbType, OutDDLFun> OUT_DDL_MAP = new HashMap<>();

    interface OutDDLFun {
        BaseOutDDL of(TableCache tableCache, List<DiffTable> diffTableList);
    }

    static {
        OUT_DDL_MAP.put(DbType.MYSQL, GenMysqlDDL::new);
    }

    public static Optional<BaseOutDDL> of(DbType dbType, TableCache tableCache, List<DiffTable> diffTableList) {
        return Optional.ofNullable(OUT_DDL_MAP.get(dbType)).map(outDDLFun -> outDDLFun.of(tableCache, diffTableList));
    }
}
