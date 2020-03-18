package cn.com.gary.dataneo4j.config;

import lombok.extern.slf4j.Slf4j;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Configuration
///@ConfigurationProperties(prefix = "spring.data.neo4j")
@Slf4j
public class Neo4jConfig {
    @Value("${spring.data.neo4j.username}")
    private String username;
    @Value("${spring.data.neo4j.password}")
    private String password;
    @Value("${spring.data.neo4j.urls}")
    private String[] urls;

    @Bean("neo4jDriver")
    public Driver neo4jDriver() throws URISyntaxException {
        log.info("username {}", username);
        log.info("password {}", password);
        log.info("urls {}", urls);

        List<URI> rout = new ArrayList<>();
//        String[] urlArr = urls.split(",");
        for (String url : urls) {
            rout.add(new URI(url));
        }
        Driver driver = GraphDatabase.routingDriver(rout, AuthTokens.basic(username, password), null);
        return driver;
    }
}
