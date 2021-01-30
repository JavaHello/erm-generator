package com.github.javahello.erm.generator.core.codegen.ddl;

import com.github.javahello.erm.generator.core.model.db.Index;

/**
 * 输出 ddl
 */
public interface ISqlIndexDel extends ICovDDL, IFixDDL {


    /**
     * 删除
     *
     * @param tbName 表名
     * @param idx    索引
     * @return ddl
     */
    ICovDDL delIdx(String tbName, Index idx);

}
