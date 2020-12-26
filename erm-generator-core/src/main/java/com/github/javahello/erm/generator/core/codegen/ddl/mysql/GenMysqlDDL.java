package com.github.javahello.erm.generator.core.codegen.ddl.mysql;

import com.github.javahello.erm.generator.core.codegen.ddl.*;
import com.github.javahello.erm.generator.core.codegen.ddl.mysql.impl.*;
import com.github.javahello.erm.generator.core.internal.TableCache;
import com.github.javahello.erm.generator.core.model.db.Column;
import com.github.javahello.erm.generator.core.model.db.Index;
import com.github.javahello.erm.generator.core.model.db.Table;
import com.github.javahello.erm.generator.core.model.diff.DiffTable;

import java.util.List;

/**
 * 生成 mysql 格式 DDL
 *
 * @author kaiv2
 */
public class GenMysqlDDL extends BaseOutDDL implements IMysqlCovDDL {

    protected ISqlTableCreate sqlTableCreate = new MysqlCreateTableGenImpl();
    protected ISqlColumnNew sqlColumnNew = new MysqlColumnNewGenImpl();
    protected ISqlColumnDel sqlColumnDel = new MysqlColumnDelGenImpl();
    protected ISqlColumnModify sqlColumnModify = new MysqlColumnModifyGenImpl();
    protected ISqlIndexNew sqlIndexNew = new MysqlIndexNewGenImpl();
    protected ISqlIndexDel sqlIndexDel = new MysqlIndexDelGenImpl();
    protected ISqlPkNew sqlPkNew = new MysqlPrimaryKeyNewGenImpl();
    protected ISqlPkDel sqlPkDel = new MysqlPrimaryKeyDelGenImpl();

    public GenMysqlDDL(TableCache newTableCache, List<DiffTable> diffTables) {
        super(newTableCache, diffTables);
    }

    @Override
    public ICovDDL tb(Table table) {
        return sqlTableCreate.tb(table);
    }


    @Override
    public ICovDDL newCol(String tbName, Column col) {
        return sqlColumnNew.newCol(tbName, col);
    }

    @Override
    public ICovDDL delCol(String tbName, Column col) {
        return sqlColumnDel.delCol(tbName, col);
    }

    @Override
    public ICovDDL modifyCol(String tbName, Column newC, Column oldC) {
        return sqlColumnModify.modifyCol(tbName, newC, oldC);
    }

    @Override
    public ICovDDL delIdx(String tbName, Index idx) {
        return sqlIndexDel.delIdx(tbName, idx);
    }

    @Override
    public ICovDDL newIdx(String tbName, Index idx) {
        return sqlIndexNew.newIdx(tbName, idx);
    }


    @Override
    public ICovDDL delPk(String tbName) {
        return sqlPkDel.delPk(tbName);
    }

    @Override
    public ICovDDL newPk(String tbName, List<Column> pks) {
        return sqlPkNew.newPk(tbName, pks);
    }
}
