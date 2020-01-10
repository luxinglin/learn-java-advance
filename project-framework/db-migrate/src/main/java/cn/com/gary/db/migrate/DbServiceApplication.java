package cn.com.gary.db.migrate;

import cn.com.gary.db.migrate.config.CustomFlywayMigrationStrategy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;

/**
 * @author luxinglin
 * @version 1.0
 * @Description: TODO
 * @create 2018-11-26 16:59
 **/
@SpringBootApplication
public class DbServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DbServiceApplication.class, args);
    }

    @Bean(name = "flywayMigrationStrategy")
    FlywayMigrationStrategy getFlywayMigrationStrategy() {
        return new CustomFlywayMigrationStrategy();
    }
}
