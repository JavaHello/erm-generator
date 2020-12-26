package com.github.javahello.erm.generator.core.codegen.ddl;

import com.github.javahello.erm.generator.core.model.db.Index;

/**
 * 输出 ddl
 */
public interface ISqlIndex {
    /**
     * 新增
     */
    ICovDDL delIdx(String tbName, Index idx);

    /**
     * 删除
     */
    ICovDDL newIdx(String tbName, Index idx);

}
