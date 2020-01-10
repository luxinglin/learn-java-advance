package cn.com.gary.biz.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author luxinglin
 * @version 1.0
 * @Description: TODO
 * @create 2019-09-20 12:13
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface BizLogParam {
    String name() default "";
}
