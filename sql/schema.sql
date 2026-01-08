-- 创建数据库
CREATE DATABASE IF NOT EXISTS personal_finance DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

USE personal_finance;

-- 分类表
DROP TABLE IF EXISTS category;
CREATE TABLE category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '分类ID',
    name VARCHAR(50) NOT NULL COMMENT '分类名称',
    type TINYINT NOT NULL COMMENT '类型：1-收入，2-支出',
    icon VARCHAR(50) DEFAULT NULL COMMENT '图标',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='收支分类表';

-- 交易记录表
DROP TABLE IF EXISTS transaction_record;
CREATE TABLE transaction_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '记录ID',
    category_id BIGINT NOT NULL COMMENT '分类ID',
    type TINYINT NOT NULL COMMENT '类型：1-收入，2-支出',
    amount DECIMAL(10,2) NOT NULL COMMENT '金额',
    remark VARCHAR(255) DEFAULT NULL COMMENT '备注',
    transaction_date DATE NOT NULL COMMENT '交易日期',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (category_id) REFERENCES category(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='交易记录表';

-- 创建索引
CREATE INDEX idx_transaction_date ON transaction_record(transaction_date);
CREATE INDEX idx_category_id ON transaction_record(category_id);
CREATE INDEX idx_type ON transaction_record(type);
