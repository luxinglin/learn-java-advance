package cn.com.gary.biz.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author luxinglin
 * @version 1.0
 * @Description: TODO
 * @create 2019-09-20 11:50
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface BizSysLog {
    /**
     * 模块名称
     *
     * @return
     */
    String module();

    /**
     * 操作描述
     *
     * @return
     */
    String description() default "";
}
