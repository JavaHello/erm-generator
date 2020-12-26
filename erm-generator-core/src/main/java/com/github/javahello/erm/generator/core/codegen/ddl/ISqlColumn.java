package com.github.javahello.erm.generator.core.codegen.ddl;

import com.github.javahello.erm.generator.core.model.db.Column;

/**
 * 输出 ddl
 */
public interface ISqlColumn extends ICovDDL {


    /**
     * 新增
     */
    ICovDDL newCol(String tbName, Column col);

    /**
     * 删除
     */
    ICovDDL delCol(String tbName, Column col);

    /**
     * 修改
     */
    ICovDDL modifyCol(String tbName, Column newC, Column oldC);

}
