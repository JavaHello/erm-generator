package com.github.javahello.erm.generator.core.codegen.ddl;

import com.github.javahello.erm.generator.core.model.db.Column;

/**
 * 输出 ddl
 */
public interface ISqlColumnModify extends ICovDDL {

    /**
     * 修改
     */
    ICovDDL modifyCol(String tbName, Column newC, Column oldC);

}
