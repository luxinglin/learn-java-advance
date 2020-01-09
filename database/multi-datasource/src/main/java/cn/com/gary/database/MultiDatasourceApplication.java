package cn.com.gary.database;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author luxinglin
 * @version 1.0
 * @Description: TODO
 * @create 2020-01-08 12:11
 **/
@SpringBootApplication
@EnableAsync
@EnableTransactionManagement(proxyTargetClass = true)
@ComponentScan(basePackages = {"cn.com.gary.biz", "cn.com.gary.database"})
@Slf4j
public class MultiDatasourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MultiDatasourceApplication.class, args);
    }

}

