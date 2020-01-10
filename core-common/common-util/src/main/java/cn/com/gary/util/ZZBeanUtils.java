package cn.com.gary.util;

import org.springframework.beans.BeanUtils;

/**
 * 复制bean
 */
public class ZZBeanUtils extends BeanUtils {
    static {
        //ConvertUtils.register(new ZZDateConverter(), java.sql.Date.class);
        //ConvertUtils.register(new ZZTimestampConverter(), java.sql.Timestamp.class);
    }
}
