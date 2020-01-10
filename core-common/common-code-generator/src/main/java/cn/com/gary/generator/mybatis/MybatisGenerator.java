package cn.com.gary.generator.mybatis;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author luxinglin
 * @version 1.0
 * @Description: TODO
 * @create 2019-08-05 14:24
 **/
@Slf4j
public class MybatisGenerator {

    public static void main(String[] args) throws Throwable {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("classpath:generator-config.xml");
        InputStream inputStream = resource.getInputStream();
        List<String> warnings = new ArrayList<>();
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(inputStream);
        DefaultShellCallback callback = new DefaultShellCallback(true);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
        log.info("Mybatis 数据模型生成成功");
    }
}
