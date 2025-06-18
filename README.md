## base 版本
### 2025-06-18---搭建基础版本 pom 
🔧 技术框架总览
层级	使用框架/库	说明
基础框架	Spring Boot 3.3.0	快速构建微服务应用，自动配置，简化开发流程
JDK版本	Java 21	LTS 版本，支持更现代的语法特性和性能优化
构建工具	Maven	项目构建和依赖管理工具

🌐 Web 层
依赖	说明
spring-boot-starter-web	提供 Spring MVC、嵌入式 Tomcat、JSON 等基础 Web 能力
springdoc-openapi-starter-webmvc-ui	用于生成在线 Swagger 接口文档，适配 Spring Boot 3+（推荐替代 Springfox）

📦 持久层
依赖	说明
mybatis-plus-boot-starter（v3.5.6）	MyBatis 增强版，简化 CRUD 操作，支持自动分页、Wrapper 条件构造等
mysql-connector-j（v8.3.0）	MySQL 官方 JDBC 驱动，连接数据库使用
spring-boot-starter-data-redis	提供 Redis 缓存支持，可用于分布式缓存、Session 管理等

📄 数据处理
依赖	说明
easyexcel（v3.3.4）	阿里开源的 Excel 读写工具，支持大数据量 Excel 读写
fastjson（v2.0.47）	高性能 JSON 序列化/反序列化库（建议在公共接口中谨慎使用，防范反序列化风险）

🧪 测试支持
依赖	说明
spring-boot-starter-test	包含 JUnit、Mockito 等常用测试框架，用于单元测试、集成测试等

🛠️ 开发效率工具
依赖	说明
lombok	简化 Java 代码开发（如自动生成 getter/setter/toString 等）

📄 项目构建与打包
插件	说明
spring-boot-maven-plugin	Spring Boot 官方打包插件，支持打成可执行的 .jar