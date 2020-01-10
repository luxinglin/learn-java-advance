package cn.com.gary.gateway;

import cn.com.gary.gateway.filter.AccessFilter;
import cn.com.gary.gateway.filter.AllowOriginFilter;
import cn.com.gary.gateway.schedule.LicenseSchedule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author luxinglin
 */
@EnableZuulProxy
@SpringBootApplication
@ComponentScan("cn.com.gary")
public class GatewayApplication {

    @Value("${skipLogin.ignoreUrls}")
    private String ignoreUrls;

    public static void main(String[] args) {
        new SpringApplicationBuilder(GatewayApplication.class).web(true).run(args);
        System.out.println("***************************gateway started***************************");
    }

    @Bean
    public AccessFilter accessFilter() {
        return new AccessFilter(ignoreUrls);
    }

    @Bean
    public AllowOriginFilter allowOriginFilter() {
        return new AllowOriginFilter();
    }

    @Bean(initMethod = "init")
    @ConditionalOnProperty(value = "license.on", matchIfMissing = false)
    public LicenseSchedule licenseSchedule() {
        return new LicenseSchedule();
    }

}
