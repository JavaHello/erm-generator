package com.github.javahello.erm.generator.core.codegen.ddl.mysql;

import com.github.javahello.erm.generator.core.codegen.ddl.*;
import com.github.javahello.erm.generator.core.internal.TableCache;
import com.github.javahello.erm.generator.core.model.db.Column;
import com.github.javahello.erm.generator.core.model.db.Index;
import com.github.javahello.erm.generator.core.model.db.Table;
import com.github.javahello.erm.generator.core.model.diff.DiffTable;
import com.github.javahello.erm.generator.core.util.DiffHelper;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 生成 mysql 格式 DDL
 *
 * @author kaiv2
 */
public class GenMysqlDDL extends BaseOutDDL implements IMysqlCovDDL {

    protected Map<String, IMysqlCovDDL> mysqlCovDDLMap = MySqlDDL.toMap();
    protected ISqlTableNew sqlTableCreate = MySqlDDL.CREATE_TABLE.getICovDDL();
    protected ISqlTableDel sqlTableDrop = MySqlDDL.DROP_TABLE.getICovDDL();
    protected ISqlColumnNew sqlColumnNew = MySqlDDL.ADD_COLUMN.getICovDDL();
    protected ISqlColumnDel sqlColumnDel = MySqlDDL.DROP_COLUMN.getICovDDL();
    protected ISqlColumnModify sqlColumnModify = MySqlDDL.CHANGE_COLUMN.getICovDDL();
    protected ISqlIndexNew sqlIndexNew = MySqlDDL.ADD_INDEX.getICovDDL();
    protected ISqlIndexDel sqlIndexDel = MySqlDDL.DROP_INDEX.getICovDDL();
    protected ISqlPkNew sqlPkNew = MySqlDDL.ADD_PRIMARY_KEY.getICovDDL();
    protected ISqlPkDel sqlPkDel = MySqlDDL.DROP_PRIMARY_KEY.getICovDDL();


    protected ICovDDL fixDdl;


    class MysqlFixDdl implements ICovDDL {

        private Collection<ICovDDL> covDDLList;

        public MysqlFixDdl() {
        }

        public void setCovDDLList(Collection<ICovDDL> covDDLList) {
            this.covDDLList = covDDLList;
        }

        @Override
        public DbType dbType() {
            return GenMysqlDDL.this.dbType();
        }

        @Override
        public String covDDL() {
            return covDDLList.stream().map(ICovDDL::covDDL).filter(DiffHelper::isNotEmpty).collect(Collectors.joining("\n"));
        }
    }

    public GenMysqlDDL(TableCache newTableCache, List<DiffTable> diffTables) {
        super(newTableCache, diffTables);
        MysqlFixDdl mysqlFixDdl = new MysqlFixDdl();
        mysqlFixDdl.setCovDDLList(mysqlCovDDLMap.values().stream().map(IFixDDL::fix).collect(Collectors.toList()));
        fixDdl = mysqlFixDdl;

    }

    @Override
    public ICovDDL newTable(Table table) {
        return sqlTableCreate.newTable(table);
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
    public ICovDDL newPk(String tbName, List<Column> pks) {
        return sqlPkNew.newPk(tbName, pks);
    }

    @Override
    public ICovDDL fix() {
        return fixDdl;
    }

    @Override
    public ICovDDL delPk(String tbName, List<Column> pks) {
        return sqlPkDel.delPk(tbName, pks);
    }

    @Override
    public ICovDDL delTable(Table table) {
        return sqlTableDrop.delTable(table);
    }
}
