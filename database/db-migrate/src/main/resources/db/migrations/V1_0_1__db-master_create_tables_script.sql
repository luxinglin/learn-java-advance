# master 数据库
CREATE TABLE if not exists `#[dbMasterSchema]`.product
(
    id    INT PRIMARY KEY AUTO_INCREMENT,
    name  VARCHAR(50)   NOT NULL,
    price DOUBLE(10, 2) NOT NULL DEFAULT 0
);
INSERT INTO `#[dbMasterSchema]`.product (name, price)
VALUES ('master', '1.0');

# slave alpha 数据库
CREATE TABLE if not exists `#[slaveAlphaSchema]`.product
(
    id    INT PRIMARY KEY AUTO_INCREMENT,
    name  VARCHAR(50)   NOT NULL,
    price DOUBLE(10, 2) NOT NULL DEFAULT 0
);
INSERT INTO `#[slaveAlphaSchema]`.product (name, price)
VALUES ('slaveAlpha', '1.1');

# slave beta 数据库
CREATE TABLE if not exists `#[slaveBetaSchema]`.product
(
    id    INT PRIMARY KEY AUTO_INCREMENT,
    name  VARCHAR(50)   NOT NULL,
    price DOUBLE(10, 2) NOT NULL DEFAULT 0
);
INSERT INTO `#[slaveBetaSchema]`.product (name, price)
VALUES ('slaveBeta', '1.2');

# slave gamma 数据库
CREATE TABLE if not exists `#[slaveGammaSchema]`.product
(
    id    INT PRIMARY KEY AUTO_INCREMENT,
    name  VARCHAR(50)   NOT NULL,
    price DOUBLE(10, 2) NOT NULL DEFAULT 0
);
INSERT INTO `#[slaveGammaSchema]`.product (name, price)
VALUES ('slaveGamma', '1.3');