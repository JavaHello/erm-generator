package com.github.javahello.erm.generator.core.codegen.ddl;

import com.github.javahello.erm.generator.core.model.db.Column;

/**
 * 输出 ddl
 */
public interface ISqlColumnDel extends ICovDDL, IFixDDL {


    /**
     * 删除
     * @param tbName 表名
     * @param col 列
     * @return ddl
     */
    ICovDDL delCol(String tbName, Column col);


}
