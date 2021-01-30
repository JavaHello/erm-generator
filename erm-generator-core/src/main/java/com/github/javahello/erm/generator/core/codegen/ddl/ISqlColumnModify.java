package com.github.javahello.erm.generator.core.codegen.ddl;

import com.github.javahello.erm.generator.core.model.db.Column;

/**
 * 输出 ddl
 */
public interface ISqlColumnModify extends ICovDDL, IFixDDL {

    /**
     * 修改
     *
     * @param tbName 表名
     * @param newC   new 列
     * @param oldC   old 列
     * @return ddl
     */
    ICovDDL modifyCol(String tbName, Column newC, Column oldC);

}
