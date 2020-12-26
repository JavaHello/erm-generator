package com.github.javahello.erm.generator.core.codegen.ddl;

import com.github.javahello.erm.generator.core.model.db.Index;

/**
 * 输出 ddl
 */
public interface ISqlIndexNew {
    /**
     * 新增
     */
    ICovDDL newIdx(String tbName, Index idx);

}
