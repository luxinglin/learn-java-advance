package cn.com.gary.database.common.config.doc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author luxinglin
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Value("${platform.url:http://127.0.0.1:9901/single-datasource/}")
    private String platformUrl;


    @Bean
    public Docket createSystemRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("1.系统管理模块")
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.com.gary.database.system.api"))
                .paths(PathSelectors.any()).build();
    }

    @Bean
    public Docket createProductRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("2.产品管理模块")
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.com.gary.database.product.api"))
                .paths(PathSelectors.any()).build();
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //标题
                .title("Multi Datasource Restful APIs")
                //简介
                .description("多数据源切换工程接口API")
                //服务条款
                .termsOfServiceUrl(platformUrl)
                //作者个人信息
                .contact(new Contact("Lu, Xing-Lin", platformUrl, "luxinglin@pioneerservice.cn"))
                //版本
                .version("1.0")
                .build();
    }
}
