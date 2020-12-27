package com.github.javahello.erm.generator.core.codegen.ddl;

import com.github.javahello.erm.generator.core.model.db.Column;

/**
 * 输出 ddl
 */
public interface ISqlColumnNew extends ICovDDL, IFixDDL {


    /**
     * 新增
     */
    ICovDDL newCol(String tbName, Column col);

}
