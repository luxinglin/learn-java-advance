## ORM 数据表到实体代码生成器


### 1. Mybatis框架
    1.1. 更新/src/main/resources/generator.properties配置文件，包括：maven.home，jdbc相关配置，基础包路径；
    
    1.2. 更新/src/main/resources/generator-config.xml配置文件，添加相关的表生成声明，形如：
        <table domainObjectName="Storage" tableName="tb_storage">
            <generatedKey column="ID" sqlStatement="MySql" identity="true"/>
        </table>
        
    1.3. 执行cn.com.gary.generator.mybatis.MybatisGenerator的main方法；
    
    1.4. 检查生成结果；
    
  
### 2. Mybatis-Plus框架
    2.1. 更新配置/src/main/resources/generator.properties文件，包括：jdbc相关配置，基础包路径，表名前缀、表名称配置，多个数据表以英文半角逗号进行分隔；
    
    2.2. 执行cn.com.gary.generator.mybatisplus.MybatisPlusGenerator的main方法；
    
    2.3. 检查生成结果；
