package cn.com.gary.database.common.config.db;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.autoconfigure.SpringBootVFS;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import com.baomidou.mybatisplus.extension.plugins.SqlExplainInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author luxinglin
 * @version 1.0
 * @Description: TODO
 * @create 2020-01-08 15:26
 **/
@Configuration
public class DataSourceConfigure {
    private final PaginationInterceptor paginationInterceptor;
    private final PerformanceInterceptor performanceInterceptor;
    private final SqlExplainInterceptor sqlExplainInterceptor;

    @Autowired
    public DataSourceConfigure(PaginationInterceptor paginationInterceptor,
                               PerformanceInterceptor performanceInterceptor,
                               SqlExplainInterceptor sqlExplainInterceptor) {
        this.paginationInterceptor = paginationInterceptor;
        this.performanceInterceptor = performanceInterceptor;
        this.sqlExplainInterceptor = sqlExplainInterceptor;
    }

    /**
     * master DataSource
     *
     * @return data source
     * @Primary 注解用于标识默认使用的 DataSource Bean，因为有5个 DataSource Bean，该注解可用于 master
     * 或 slave DataSource Bean, 但不能用于 dynamicDataSource Bean, 否则会产生循环调用
     * @ConfigurationProperties 注解用于从 application.properties 文件中读取配置，为 Bean 设置属性
     */
    @Bean("master")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.druid.master")
    public DataSource master() {
        return DruidDataSourceBuilder.create().build();
    }


    /**
     * 配置 SqlSessionFactoryBean
     *
     * @return the sql session factory bean
     * @ConfigurationProperties 在这里是为了将 MyBatis 的 mapper 位置和持久层接口的别名设置到
     * Bean 的属性中，如果没有使用 *.xml 则可以不用该配置，否则将会产生 invalid bond statement 异常
     */

    @Bean("sqlSessionFactoryBean")
    @ConfigurationProperties(prefix = "mybatis-plus")
    public MybatisSqlSessionFactoryBean sqlSessionFactoryBean() throws IOException {
        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        // 配置数据源，此处配置为关键配置，如果没有将 dynamicDataSource 作为数据源则不能实现切换
        sqlSessionFactoryBean.setDataSource(master());
        Interceptor[] interceptors = new Interceptor[]{paginationInterceptor, performanceInterceptor, sqlExplainInterceptor};
        sqlSessionFactoryBean.setVfs(SpringBootVFS.class);
        sqlSessionFactoryBean.setPlugins(interceptors);
        sqlSessionFactoryBean.setFailFast(true);
        return sqlSessionFactoryBean;
    }

    /**
     * 注入 DataSourceTransactionManager 用于事务管理
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(master());
    }

}
