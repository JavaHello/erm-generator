package com.github.javahello.erm.generator.core.codegen.ddl;

import com.github.javahello.erm.generator.core.model.db.Index;

/**
 * 输出 ddl
 */
public interface ISqlIndexNew extends ICovDDL, IFixDDL {

    /**
     * 新增
     *
     * @param tbName 表名
     * @param idx    索引
     * @return ddl
     */
    ICovDDL newIdx(String tbName, Index idx);

}
