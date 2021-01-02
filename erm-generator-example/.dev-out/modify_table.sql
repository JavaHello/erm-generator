CREATE TABLE USER_INFO (
    ID bigint NOT NULL COMMENT '主键ID',
    UNAME varchar(32) NOT NULL COMMENT '用户名',
    CREATED_DATETIME datetime NULL COMMENT '创建时间',
    ID_NO varchar(18) NULL COMMENT '身份证',
    PRIMARY KEY (ID, UNAME),
    IDX_01INDEX IDX_01 (ID_NO)
) COMMENT = 'USER_INFO'
ENGINE=InnoDB
;
CREATE TABLE USER_CONFIG (
    CONF_ID varchar(32) NOT NULL COMMENT 'CONF_ID',
    CONF_VALUE varchar(255) NULL COMMENT 'CONF_VALUE',
    USE_RATE decimal(5) NULL COMMENT 'USE_RATE',
    CREATED_DATETIME datetime NULL COMMENT '创建时间',
    LAST_MODIFIED_DATETIME datetime NULL COMMENT '最后修改时间',
    PRIMARY KEY (CONF_ID)
) COMMENT = 'USER_CONFIG'
ENGINE=InnoDB
;
