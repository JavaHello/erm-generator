package com.github.javahello.erm.generator.core.codegen.ddl;

import com.github.javahello.erm.generator.core.model.db.Column;

/**
 * 输出 ddl
 */
public interface ISqlColumnNew extends ICovDDL, IFixDDL {


    /**
     * 新增
     *
     * @param tbName 表名
     * @param col    列
     * @return ddl
     */
    ICovDDL newCol(String tbName, Column col);

}
