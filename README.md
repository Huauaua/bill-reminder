# 个人记账系统

基于 Spring Boot + MySQL + JDBC + Thymeleaf 的个人记账系统，用于记录日常收支情况。

## 一、技术栈

| 技术 | 版本 | 说明 |
|------|------|------|
| Spring Boot | 2.6.13 | 核心框架 |
| MySQL | 8.x | 数据库 |
| Spring JDBC | - | 数据访问层 |
| Thymeleaf | - | 模板引擎 |
| Bootstrap | 5.3.0 | 前端UI框架 |
| jQuery | 3.6.0 | JavaScript库 |
| Lombok | - | 简化代码 |

## 二、系统功能

### 2.1 收支记录
- 记录每一笔支出/收入
- 支持选择分类（餐饮、交通、购物、工资、兼职等）
- 填写金额、备注、日期

### 2.2 数据统计
- 总收入/总支出/结余统计
- 本月收支统计
- 最近交易记录展示

### 2.3 记录管理
- 新增收支记录
- 编辑收支记录
- 删除收支记录
- 按类型筛选（全部/收入/支出）

## 三、项目结构

```
bill-reminder/
├── sql/                                    # 数据库脚本
│   ├── schema.sql                          # 建表脚本
│   └── data.sql                            # 测试数据
├── src/main/java/com/zts/billreminder/
│   ├── BillReminderApplication.java        # 启动类
│   ├── controller/                         # 控制层
│   │   ├── HomeController.java             # 首页控制器
│   │   └── TransactionController.java      # 交易记录控制器
│   ├── entity/                             # 实体类
│   │   ├── Category.java                   # 分类实体
│   │   └── TransactionRecord.java          # 交易记录实体
│   ├── repository/                         # 数据访问层
│   │   ├── CategoryRepository.java         # 分类Repository
│   │   └── TransactionRepository.java      # 交易Repository
│   └── service/                            # 服务层
│       ├── CategoryService.java            # 分类服务
│       └── TransactionService.java         # 交易服务
├── src/main/resources/
│   ├── application.properties              # 配置文件
│   └── templates/                          # 页面模板
│       ├── index.html                      # 首页
│       └── transaction/
│           ├── form.html                   # 记账表单
│           └── list.html                   # 记录列表
└── pom.xml                                 # Maven配置
```

## 四、数据库设计

### 4.1 分类表 (category)

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键ID |
| name | VARCHAR(50) | 分类名称 |
| type | TINYINT | 类型：1-收入，2-支出 |
| icon | VARCHAR(50) | 图标 |
| create_time | DATETIME | 创建时间 |
| update_time | DATETIME | 更新时间 |

### 4.2 交易记录表 (transaction_record)

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键ID |
| category_id | BIGINT | 分类ID |
| type | TINYINT | 类型：1-收入，2-支出 |
| amount | DECIMAL(10,2) | 金额 |
| remark | VARCHAR(255) | 备注 |
| transaction_date | DATE | 交易日期 |
| create_time | DATETIME | 创建时间 |
| update_time | DATETIME | 更新时间 |

## 五、运行指南

### 5.1 环境要求
- JDK 11+
- MySQL 8.x
- Maven 3.6+

### 5.2 数据库初始化

1. 创建数据库并执行建表脚本：
```bash
mysql -u root -p < sql/schema.sql
```

2. 导入测试数据：
```bash
mysql -u root -p < sql/data.sql
```

### 5.3 配置数据库连接

修改 `src/main/resources/application.properties`：
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/personal_finance?useSSL=false&serverTimezone=Asia/Shanghai
spring.datasource.username=root
spring.datasource.password=你的密码
```

### 5.4 启动项目

方式一：使用Maven命令
```bash
mvn spring-boot:run
```

方式二：使用IDE
- 运行 `BillReminderApplication.java` 主类

### 5.5 访问系统

启动成功后，访问：http://localhost:8080

## 六、部署指南

### 6.1 打包

```bash
mvn clean package -DskipTests
```

生成的jar包位于 `target/bill-reminder-0.0.1-SNAPSHOT.jar`

### 6.2 运行

```bash
java -jar target/bill-reminder-0.0.1-SNAPSHOT.jar
```

### 6.3 指定配置

```bash
java -jar bill-reminder-0.0.1-SNAPSHOT.jar --spring.datasource.password=你的密码
```

## 七、页面预览

### 7.1 首页
- 显示总收入、总支出、结余统计
- 显示本月收支统计
- 快捷记账入口
- 最近交易记录

### 7.2 记录列表
- 展示所有收支记录
- 支持按类型筛选
- 支持编辑、删除操作

### 7.3 记账表单
- 选择收入/支出类型
- 选择分类
- 填写金额、日期、备注
