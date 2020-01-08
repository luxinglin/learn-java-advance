package cn.com.gary.util.web;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;

/**
 * @author luxinglin
 * @version 1.0
 * @Description: TODO
 * @create 2020-01-08 22:24
 **/
@Slf4j
public class WebUtil {
    /**
     * 创建时间属性
     */
    private final static String CREATED_DT_PROP = "createdDt";
    /**
     * 创建人属性
     */
    private final static String CREATED_BY_PROP = "createdBy";
    /**
     * 更新时间属性
     */
    private final static String UPDATED_DT_PROP = "updatedDt";
    /**
     * 更新人属性
     */
    private final static String UPDATED_BY_PROP = "updatedBy";
    /**
     * 版本属性
     */
    private final static String VERSION_PROP = "version";
    /**
     * 国际化资源文件
     */
    public static MessageSource messageSource;

    static {
        try {
            // 获取消息处理类
            messageSource = new ClassPathXmlApplicationContext("classpath:spring-message-bean.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private WebUtil() {
    }

    /**
     * 取得系统消息
     *
     * @param msgId 消息ID
     * @return 消息内容
     */
    public static String getMessage(String msgId) {
        return WebUtil.getMessage(msgId, null);
    }

    /**
     * 取得系统消息
     *
     * @param msgId 消息ID
     * @param arg   消息设置参数
     * @return 消息内容
     */
    public static String getMessage(String msgId, Object[] arg) {
        String message = StringUtils.EMPTY;
        try {
            message = messageSource.getMessage(msgId, arg, Locale.CHINA);
        } catch (Exception e) {
        }

        return message;
    }

    /**
     * 新增时添加用户以及当前时间信息
     */
    public static <T> void prepareInsertParams(T obj) {
        prepareInsertParams(obj, null);
    }

    /**
     * 新增时添加用户以及当前时间信息(For Activiti)
     */
    private static <T> void prepareInsertParams(T obj, String user) {
        if (user != null && !StringUtils.EMPTY.equals(user)) {
            // 创建人
            invokeSet(obj, CREATED_BY_PROP, user);
            // 更新人
            invokeSet(obj, UPDATED_BY_PROP, user);
        }

        Date date = new Date();
        // 更新时间
        invokeSet(obj, CREATED_DT_PROP, date);
        // 更新时间
        invokeSet(obj, UPDATED_DT_PROP, date);
        // 初始版本号
        invokeSet(obj, VERSION_PROP, 1L);
    }

    /**
     * 更新时添加用户以及当前时间信息
     */
    public static <T> void prepareUpdateParams(T obj) {
        prepareUpdateParams(obj, null);
    }

    /**
     * 更新时添加用户以及当前时间信息
     */
    public static <T> void prepareUpdateParams(T obj, String user) {
        if (user != null && !StringUtils.EMPTY.equals(user)) {
            // 更新人
            invokeSet(obj, UPDATED_BY_PROP, user);
        }
        // 更新时间
        Date date = new Date();
        invokeSet(obj, UPDATED_DT_PROP, date);
    }

    /**
     * 执行set方法
     *
     * @param o         执行对象
     * @param fieldName 属性
     * @param value     值
     */
    private static void invokeSet(Object o, String fieldName, Object value) {
        Method method = getSetMethod(o.getClass(), fieldName);
        try {
            Type type = method.getGenericParameterTypes()[0];
            //初始版本设置
            if (VERSION_PROP.equals(fieldName)) {
                if (Integer.class.getName().equals(type.getTypeName())) {
                    method.invoke(o, new Object[]{1});
                } else if (Long.class.getName().equals(type.getTypeName())) {
                    method.invoke(o, new Object[]{1L});
                }
            }

            if (LocalDateTime.class.getName().equals(type.getTypeName())) {
                method.invoke(o, new Object[]{LocalDateTime.now()});
            } else {
                method.invoke(o, new Object[]{value});
            }
        } catch (Exception e) {
            log.warn("invokeSet failed, reason: {}", e.getMessage());
        }
    }

    /**
     * java反射bean的set方法
     */
    private static Method getSetMethod(Class<? extends Object> objectClass, String fieldName) {
        try {
            Class[] parameterTypes = new Class[1];
            Field field = getField(objectClass, fieldName);
            parameterTypes[0] = field.getType();
            StringBuffer sb = new StringBuffer();
            sb.append("set");
            sb.append(fieldName.substring(0, 1).toUpperCase());
            sb.append(fieldName.substring(1));
            return objectClass.getMethod(sb.toString(), parameterTypes);
        } catch (Exception e) {
            log.warn("getSetMethod failed, reason: {}", e.getMessage());
        }
        return null;

    }

    /**
     * 获取类已经类的父的某一声明变量
     *
     * @param fieldName 变量名
     * @return Field 变量
     */
    private static Field getField(Class clazz, String fieldName) throws NoSuchFieldException {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            Class superClass = clazz.getSuperclass();
            if (superClass == null) {
                throw e;
            } else {
                return getField(superClass, fieldName);
            }
        }
    }


}
