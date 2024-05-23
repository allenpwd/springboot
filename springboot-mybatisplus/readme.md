### flyway
Flyway是独立于数据库的应用、管理并跟踪数据库变更的数据库版本管理工具。\
用通俗的话讲，Flyway可以像Git管理不同人的代码那样，管理不同人的sql脚本，从而做到数据库同步。

#### 流程
1. 首先配置好flyway的基本信息后，运行项目，会在数据库表中默认新建一个数据表用于存储flyway的运行信息，默认的数据库名：flyway_schema_history
2. 紧接着Flyway将开始扫描文件系统或应用程序的类路径进行迁移。然后，Flyway的数据迁移将基于对用sql脚本的版本号进行排序，并按顺序应用：
可以看到执行数据库表后在checksum中储存一个数值，用于在之后运行过程中对比sql文件执行是否有变化。
注意：
flyway在执行脚本时，会在源数据表中检查checksum值，并确定上次运行到哪一个脚本文件，本次执行时从下一条脚本文件开始执行。所以编写脚本的时候不要去修改原有的脚本内容，并且新的脚本版本号要连续

#### 问题
不会自动创建schemas
- 有一个属性 spring.flyway.create-schemas（或旧版中的 spring.flyway.init-sql），当其被设置为 true 时，Flyway 会在执行迁移前尝试创建指定的 schema（在 MySQL 中通常称为 database）。默认是true
- mysql可以通过添加createDatabaseIfNotExist=true 参数来让 MySQL 驱动在连接时自动创建不存在的数据库。
- 有时，即使配置正确，如果应用程序使用的数据库用户没有足够的权限去创建数据库，也会导致 Unknown database 错误。确保数据库用户拥有 CREATE 权限。

### TODO
- 敏感词
