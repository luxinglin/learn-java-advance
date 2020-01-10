package cn.com.gary.database.common.config.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;

/**
 * @author luxinglin
 * @version 1.0
 * @Description: TODO
 * @create 2020-01-08 15:25
 **/
@Slf4j
public class DynamicRoutingDataSource extends AbstractRoutingDataSource implements
        ApplicationContextAware {
    ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @Override
    protected Object determineCurrentLookupKey() {
        Object key = DynamicDataSourceContextHolder.getDataSourceKey();
        log.info("Current DataSource is [{}]", key);
        DataSource dataSource = (DataSource) context.getBean(key.toString());
        return key;
    }
}
