package cn.com.gary.db.migrate.config;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * @author luxinglin
 * @version 1.0
 * @Description: TODO
 * @create 2018-11-26 17:10
 **/
@Configuration
@Primary
@Slf4j
public class DataSourceConfig {
    @Value("${spring.datasource.url}")
    private String datasourceUrl;
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    @Value("${spring.datasource.username:root}")
    private String username;
    @Value("${spring.datasource.password:123456}")
    private String password;

    @Bean(name = "dataSource")
    public DataSource dataSource() {

        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(datasourceUrl);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setDriverClassName(driverClassName);

        try {
            Class.forName(driverClassName);
            String url01 = datasourceUrl.substring(0, datasourceUrl.indexOf("?"));
            String url02 = url01.substring(0, url01.lastIndexOf("/"));
            String datasourceName = url01.substring(url01.lastIndexOf("/") + 1);
            // 连接已经存在的数据库，如：mysql
            Connection connection = DriverManager.getConnection(url02, username, password);
            Statement statement = connection.createStatement();
            // 创建数据库
            statement.executeUpdate("create database if not exists `" + datasourceName + "` default character set utf8mb4 COLLATE utf8mb4_general_ci");
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return datasource;
    }

}
