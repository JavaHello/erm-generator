package com.github.javahello.erm.generator.core.codegen.ddl;

public interface ISqlAll extends ISqlColumnNew, ISqlColumnDel, ISqlColumnModify,
        ISqlIndexNew, ISqlIndexDel,
        ISqlPkNew, ISqlPkDel,
        ISqlTableNew, ISqlTableDel,
        ICovDDL {
}
