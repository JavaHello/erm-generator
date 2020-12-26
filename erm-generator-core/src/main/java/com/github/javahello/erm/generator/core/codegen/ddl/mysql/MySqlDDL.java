package com.github.javahello.erm.generator.core.codegen.ddl.mysql;

import com.github.javahello.erm.generator.core.codegen.ddl.ICovDDL;

import java.util.Objects;

/**
 * @author kaiv2
 */
public enum MySqlDDL {

    /**
     * CREATE TABLE `T` (
     * 	`A` VARCHAR(32) NOT NULL COMMENT 'test',
     * 	`B` VARCHAR(8) NULL DEFAULT NULL,
     * 	`C` INT(1) NOT NULL DEFAULT '0',
     * 	PRIMARY KEY (`A`) USING BTREE,
     * 	UNIQUE INDEX `IDX_B` (`B`)
     * )
     * COMMENT='T'
     * COLLATE='utf8_general_ci'
     * ENGINE=InnoDB
     * ;
     */
    CREATE_TABLE(null),

    /**
     * ALTER TABLE `T`
     * ADD COLUMN `C` INT NOT NULL DEFAULT '0' COMMENT 'test';
     */
    ADD_COLUMN(null),
    /**
     * ALTER TABLE `T`
     * DROP COLUMN `C`;
     */
    DROP_COLUMN(null),

    /**
     * ALTER TABLE `T`
     * CHANGE COLUMN `A` `B` INT NOT NULL DEFAULT '0' COMMENT 'test' ;
     */
    CHANGE_COLUMN(null),

    /**
     * ALTER TABLE `T`
     * ADD INDEX `IDX` (`A`);
     */
    ADD_INDEX(null),

    /**
     * ALTER TABLE `T`
     * DROP INDEX `A`;
     */
    DROP_INDEX(null),

    /**
     * ALTER TABLE `T`
     * ADD PRIMARY KEY (`USER_NO`);
     */
    ADD_PRIMARY_KEY(null),

    /**
     * ALTER TABLE `T`
     * DROP PRIMARY KEY;
     */
    DROP_PRIMARY_KEY(null),
    ;

    private IMysqlCovDDL mysqlCovDDL;

    MySqlDDL(IMysqlCovDDL mysqlCovDDL) {
        Objects.requireNonNull(mysqlCovDDL);
        this.mysqlCovDDL = mysqlCovDDL;
    }


    public ICovDDL getICovDDL() {
        return mysqlCovDDL;
    }
}
