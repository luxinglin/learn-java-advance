package cn.com.gary.database.common.config.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author luxinglin
 * @since 2019-09-19
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    /**
     * 静态图片资源获取的restful url
     */
    public final static String IMAGE_URL = "/image/";
    /**
     * 静态附件资源获取的restful url
     */
    public final static String ATTACHMENT_URL = "/attachment/";
    @Value("${image.base.path:/data/images/}")
    private String imageBasePath;
    @Value("${attachment.base.path:/data/attachments/}")
    private String attachmentBasePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 解决 swagger-ui.html 404报错
        registry.addResourceHandler("/swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        // 解决 doc.html 404 报错
        registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
        // 图片拦截
        registry.addResourceHandler(IMAGE_URL.concat("**")).addResourceLocations("file:".concat(imageBasePath));
        // 附件拦截
        registry.addResourceHandler(ATTACHMENT_URL.concat("**")).addResourceLocations("file:".concat(attachmentBasePath));
    }
}