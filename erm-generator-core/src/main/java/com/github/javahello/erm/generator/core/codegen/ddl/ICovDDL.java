package com.github.javahello.erm.generator.core.codegen.ddl;

/**
 * 转换为 ddl
 * @author kaiv2
 */
public interface ICovDDL {

    DbType dbType();

    String covDDL();
}
