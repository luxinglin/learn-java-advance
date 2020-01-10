package cn.com.gary.generator.common;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

/**
 * @author luxinglin
 * @version 1.0
 * @Description: TODO
 * @create 2019-08-05 17:05
 **/
public class PropertyUtil {
    /**
     * 配置信息
     */
    private static Properties props;

    /**
     * 获取配置文件中指定key对应的值
     *
     * @param key
     * @return
     */
    public static String getPropertyValue(String key) throws IOException {
        return getPropertyValue(key, "");
    }

    /**
     * 获取配置文件中指定key对应的值
     *
     * @param key
     * @param defaultValue 默认值
     * @return
     */
    private static String getPropertyValue(String key, String defaultValue) throws IOException {
        return props == null ? null : props.getProperty(key, defaultValue);
    }

    /**
     * 获取配置文件信息
     *
     * @param filePath 配置文件位置
     * @throws IOException
     */
    public static void getProperties(String filePath) throws IOException {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource(filePath);
        props = PropertiesLoaderUtils.loadProperties(resource);
    }
}
