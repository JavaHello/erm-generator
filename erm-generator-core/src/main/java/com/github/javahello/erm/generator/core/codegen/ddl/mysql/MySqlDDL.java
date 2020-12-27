package com.github.javahello.erm.generator.core.codegen.ddl.mysql;

import com.github.javahello.erm.generator.core.codegen.ddl.ICovDDL;
import com.github.javahello.erm.generator.core.codegen.ddl.mysql.impl.*;
import com.github.javahello.erm.generator.core.util.MapHelper;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * @author kaiv2
 */
public enum MySqlDDL implements IMysqlCovDDL {

    /**
     * CREATE TABLE `T` (
     * `A` VARCHAR(32) NOT NULL COMMENT 'test',
     * `B` VARCHAR(8) NULL DEFAULT NULL,
     * `C` INT(1) NOT NULL DEFAULT '0',
     * PRIMARY KEY (`A`) USING BTREE,
     * UNIQUE INDEX `IDX_B` (`B`)
     * )
     * COMMENT='T'
     * COLLATE='utf8_general_ci'
     * ENGINE=InnoDB
     * ;
     */
    CREATE_TABLE(MysqlTableNewGenImpl::new, MysqlTableDelGenImpl::new),

    DROP_TABLE(MysqlTableDelGenImpl::new, MysqlTableNewGenImpl::new),

    /**
     * ALTER TABLE `T`
     * ADD COLUMN `C` INT NOT NULL DEFAULT '0' COMMENT 'test';
     */
    ADD_COLUMN(MysqlColumnNewGenImpl::new, MysqlColumnDelGenImpl::new),
    /**
     * ALTER TABLE `T`
     * DROP COLUMN `C`;
     */
    DROP_COLUMN(MysqlColumnDelGenImpl::new, MysqlColumnNewGenImpl::new),

    /**
     * ALTER TABLE `T`
     * CHANGE COLUMN `A` `B` INT NOT NULL DEFAULT '0' COMMENT 'test' ;
     */
    CHANGE_COLUMN(MysqlColumnModifyGenImpl::new, MysqlColumnModifyGenImpl::new),

    /**
     * ALTER TABLE `T`
     * ADD INDEX `IDX` (`A`);
     */
    ADD_INDEX(MysqlIndexNewGenImpl::new, MysqlIndexDelGenImpl::new),

    /**
     * ALTER TABLE `T`
     * DROP INDEX `A`;
     */
    DROP_INDEX(MysqlIndexDelGenImpl::new, MysqlIndexNewGenImpl::new),

    /**
     * ALTER TABLE `T`
     * ADD PRIMARY KEY (`USER_NO`);
     */
    ADD_PRIMARY_KEY(MysqlPrimaryKeyNewGenImpl::new, MysqlPrimaryKeyDelGenImpl::new),

    /**
     * ALTER TABLE `T`
     * DROP PRIMARY KEY;
     */
    DROP_PRIMARY_KEY(MysqlPrimaryKeyDelGenImpl::new, MysqlPrimaryKeyNewGenImpl::new),
    ;

    private final AbstractMysqlCovDDL<?> mysqlCovDDL;
    private ICovDDL fixMysqlCovDDL;

    MySqlDDL(Supplier<AbstractMysqlCovDDL<?>> mysqlCovDDLSupplier, Supplier<ICovDDL> fiXMysqlCovDDLSupplier) {
        Objects.requireNonNull(mysqlCovDDLSupplier);
        this.mysqlCovDDL = mysqlCovDDLSupplier.get();
        Objects.requireNonNull(mysqlCovDDL);
        Optional.ofNullable(fiXMysqlCovDDLSupplier)
                .ifPresent(fix -> {
                    this.fixMysqlCovDDL = fix.get();
                    mysqlCovDDL.setFixDdl(fixMysqlCovDDL);
                });
    }


    @SuppressWarnings("unchecked")
    public <T extends IMysqlCovDDL> T getICovDDL() {
        return (T) mysqlCovDDL;
    }

    @Override
    public String covDDL() {
        return mysqlCovDDL.covDDL();
    }

    @Override
    public ICovDDL fix() {
        return fixMysqlCovDDL;
    }

    public static Map<String, IMysqlCovDDL> toMap() {
        return MapHelper.uniqueGroup(values(), e -> ((MySqlDDL) e).name());
    }
}
