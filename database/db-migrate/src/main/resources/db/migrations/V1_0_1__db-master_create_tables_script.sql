# master 数据库
CREATE TABLE if not exists `#[dbMasterSchema]`.product
(
    ID         INT PRIMARY KEY AUTO_INCREMENT comment '主键ID',
    `NAME`     VARCHAR(50)   NOT NULL comment '名称',
    PRICE      DOUBLE(10, 2) NOT NULL DEFAULT 0 comment '价格',
    CREATED_DT datetime      null     default null comment '创建时间',
    CREATED_BY BIGINT        null     default null comment '创建人',
    UPDATED_DT datetime      null     default null comment '更新时间',
    UPDATED_BY BIGINT        null     default null comment '更新人',
    VERSION    BIGINT        null     default 1 comment '版本'
);
INSERT INTO `#[dbMasterSchema]`.product (`NAME`, PRICE)
VALUES ('master', '1.0');

# slave alpha 数据库
CREATE TABLE if not exists `#[slaveAlphaSchema]`.product
(
    ID         INT PRIMARY KEY AUTO_INCREMENT comment '主键ID',
    `NAME`     VARCHAR(50)   NOT NULL comment '名称',
    PRICE      DOUBLE(10, 2) NOT NULL DEFAULT 0 comment '价格',
    CREATED_DT datetime      null     default null comment '创建时间',
    CREATED_BY BIGINT        null     default null comment '创建人',
    UPDATED_DT datetime      null     default null comment '更新时间',
    UPDATED_BY BIGINT        null     default null comment '更新人',
    VERSION    BIGINT        null     default 1 comment '版本'
);
INSERT INTO `#[slaveAlphaSchema]`.product (`NAME`, PRICE)
VALUES ('slaveAlpha', '1.1');

# slave beta 数据库
CREATE TABLE if not exists `#[slaveBetaSchema]`.product
(
    ID         INT PRIMARY KEY AUTO_INCREMENT comment '主键ID',
    `NAME`     VARCHAR(50)   NOT NULL comment '名称',
    PRICE      DOUBLE(10, 2) NOT NULL DEFAULT 0 comment '价格',
    CREATED_DT datetime      null     default null comment '创建时间',
    CREATED_BY BIGINT        null     default null comment '创建人',
    UPDATED_DT datetime      null     default null comment '更新时间',
    UPDATED_BY BIGINT        null     default null comment '更新人',
    VERSION    BIGINT        null     default 1 comment '版本'
);
INSERT INTO `#[slaveBetaSchema]`.product (`NAME`, PRICE)
VALUES ('slaveBeta', '1.2');

# slave gamma 数据库
CREATE TABLE if not exists `#[slaveGammaSchema]`.product
(
    ID         INT PRIMARY KEY AUTO_INCREMENT comment '主键ID',
    `NAME`     VARCHAR(50)   NOT NULL comment '名称',
    PRICE      DOUBLE(10, 2) NOT NULL DEFAULT 0 comment '价格',
    CREATED_DT datetime      null     default null comment '创建时间',
    CREATED_BY BIGINT        null     default null comment '创建人',
    UPDATED_DT datetime      null     default null comment '更新时间',
    UPDATED_BY BIGINT        null     default null comment '更新人',
    VERSION    BIGINT        null     default 1 comment '版本'
);
INSERT INTO `#[slaveGammaSchema]`.product (`NAME`, PRICE)
VALUES ('slaveGamma', '1.3');