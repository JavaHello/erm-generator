package com.github.javahello.erm.generator.core.codegen.ddl;

import com.github.javahello.erm.generator.core.model.db.Column;

import java.util.List;

/**
 * 输出 ddl
 */
public interface ISqlPkNew extends ICovDDL, IFixDDL {


    /**
     * 新增
     *
     * @param tbName 表名
     * @param pks    主键
     * @return ddl
     */
    ICovDDL newPk(String tbName, List<Column> pks);

}
