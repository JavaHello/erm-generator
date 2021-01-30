package com.github.javahello.erm.generator.core.codegen.ddl;

import com.github.javahello.erm.generator.core.model.db.Column;

import java.util.List;

/**
 * 输出 ddl
 */
public interface ISqlPkDel extends ICovDDL, IFixDDL {


    /**
     * 删除
     *
     * @param tbName 表名
     * @param pks    主键
     * @return ddl
     */
    ICovDDL delPk(String tbName, List<Column> pks);

}
