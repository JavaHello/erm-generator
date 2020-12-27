package com.github.javahello.erm.generator.core.codegen.ddl;

import com.github.javahello.erm.generator.core.model.db.Column;

/**
 * 输出 ddl
 */
public interface ISqlColumnDel extends ICovDDL, IFixDDL {


    /**
     * 删除
     */
    ICovDDL delCol(String tbName, Column col);


}
