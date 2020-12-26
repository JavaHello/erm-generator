package com.github.javahello.erm.generator.core.codegen.ddl;

import com.github.javahello.erm.generator.core.model.db.Column;

/**
 * 输出 ddl
 */
public interface ISqlPks {
    /**
     * 新增
     */
    ICovDDL delPk(String tbName, Column pk);

    /**
     * 删除
     */
    ICovDDL newPk(String tbName, Column pk);

}
