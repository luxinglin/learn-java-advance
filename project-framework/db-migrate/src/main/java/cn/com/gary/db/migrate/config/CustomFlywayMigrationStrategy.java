package cn.com.gary.db.migrate.config;

import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;

/**
 * @author luxinglin
 * @version 1.0
 * @Description: TODO
 * @create 2019-01-07 17:35
 **/
@Slf4j
public class CustomFlywayMigrationStrategy implements FlywayMigrationStrategy {

    @Override
    public void migrate(Flyway flyway) {
        log.info("execute custom migration goal");
        flyway.repair();
        flyway.setOutOfOrder(true);
        flyway.migrate();
    }
}
