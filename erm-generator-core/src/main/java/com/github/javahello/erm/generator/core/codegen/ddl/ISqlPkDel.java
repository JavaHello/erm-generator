package com.github.javahello.erm.generator.core.codegen.ddl;

/**
 * 输出 ddl
 */
public interface ISqlPkDel {
    /**
     * 删除
     */
    ICovDDL delPk(String tbName);

}
