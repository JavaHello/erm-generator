package com.github.javahello.erm.generator.core.codegen.ddl;

import com.github.javahello.erm.generator.core.model.db.Index;

/**
 * 输出 ddl
 */
public interface ISqlIndexDel {
    /**
     * 删除
     */
    ICovDDL delIdx(String tbName, Index idx);

}
