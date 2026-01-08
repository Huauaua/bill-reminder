-- 在文件最开头添加这些设置
SET NAMES utf8mb4;
SET CHARACTER SET utf8mb4;
SET character_set_connection = utf8mb4;
SET character_set_client = utf8mb4;
SET character_set_results = utf8mb4;

USE personal_finance;

-- 1. 查看当前数据库字符集
SHOW CREATE DATABASE personal_finance;

-- 2. 查看表字符集
SHOW CREATE TABLE category;
SHOW CREATE TABLE transaction_record;

-- 3. 如果字符集不是utf8mb4，修改数据库字符集
ALTER DATABASE personal_finance CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 4. 修改表字符集
ALTER TABLE category CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
ALTER TABLE transaction_record CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 5. 也可以单独修改列的字符集
ALTER TABLE category MODIFY name VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
ALTER TABLE category MODIFY icon VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
ALTER TABLE transaction_record MODIFY remark VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 初始化分类数据
-- 支出分类
INSERT INTO category (name, type, icon) VALUES ('餐饮', 2, 'bi-cup-hot');
INSERT INTO category (name, type, icon) VALUES ('交通', 2, 'bi-bus-front');
INSERT INTO category (name, type, icon) VALUES ('购物', 2, 'bi-cart');
INSERT INTO category (name, type, icon) VALUES ('娱乐', 2, 'bi-controller');
INSERT INTO category (name, type, icon) VALUES ('通讯', 2, 'bi-phone');
INSERT INTO category (name, type, icon) VALUES ('住房', 2, 'bi-house');
INSERT INTO category (name, type, icon) VALUES ('医疗', 2, 'bi-hospital');
INSERT INTO category (name, type, icon) VALUES ('教育', 2, 'bi-book');
INSERT INTO category (name, type, icon) VALUES ('其他支出', 2, 'bi-three-dots');

-- 收入分类
INSERT INTO category (name, type, icon) VALUES ('工资', 1, 'bi-cash');
INSERT INTO category (name, type, icon) VALUES ('兼职', 1, 'bi-briefcase');
INSERT INTO category (name, type, icon) VALUES ('投资收益', 1, 'bi-graph-up');
INSERT INTO category (name, type, icon) VALUES ('奖金', 1, 'bi-gift');
INSERT INTO category (name, type, icon) VALUES ('其他收入', 1, 'bi-plus-circle');

-- 测试数据 - 交易记录
-- 支出记录
INSERT INTO transaction_record (category_id, type, amount, remark, transaction_date) VALUES (1, 2, 35.00, '午餐', '2026-01-01');
INSERT INTO transaction_record (category_id, type, amount, remark, transaction_date) VALUES (1, 2, 28.50, '晚餐', '2026-01-01');
INSERT INTO transaction_record (category_id, type, amount, remark, transaction_date) VALUES (2, 2, 5.00, '地铁', '2026-01-01');
INSERT INTO transaction_record (category_id, type, amount, remark, transaction_date) VALUES (1, 2, 42.00, '午餐', '2026-01-02');
INSERT INTO transaction_record (category_id, type, amount, remark, transaction_date) VALUES (2, 2, 15.00, '打车', '2026-01-02');
INSERT INTO transaction_record (category_id, type, amount, remark, transaction_date) VALUES (3, 2, 199.00, '买衣服', '2026-01-03');
INSERT INTO transaction_record (category_id, type, amount, remark, transaction_date) VALUES (5, 2, 50.00, '话费充值', '2026-01-03');
INSERT INTO transaction_record (category_id, type, amount, remark, transaction_date) VALUES (4, 2, 80.00, '电影票', '2026-01-04');
INSERT INTO transaction_record (category_id, type, amount, remark, transaction_date) VALUES (1, 2, 66.00, '聚餐', '2026-01-05');
INSERT INTO transaction_record (category_id, type, amount, remark, transaction_date) VALUES (6, 2, 2000.00, '房租', '2026-01-05');

-- 收入记录
INSERT INTO transaction_record (category_id, type, amount, remark, transaction_date) VALUES (10, 1, 15000.00, '1月工资', '2026-01-05');
INSERT INTO transaction_record (category_id, type, amount, remark, transaction_date) VALUES (11, 1, 500.00, '周末兼职', '2026-01-06');
INSERT INTO transaction_record (category_id, type, amount, remark, transaction_date) VALUES (12, 1, 200.00, '基金分红', '2026-01-06');
