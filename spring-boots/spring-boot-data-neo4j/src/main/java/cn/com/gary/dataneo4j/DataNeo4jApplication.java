package cn.com.gary.dataneo4j;

import cn.com.gary.dataneo4j.config.Neo4jConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class DataNeo4jApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataNeo4jApplication.class, args);
	}

}
