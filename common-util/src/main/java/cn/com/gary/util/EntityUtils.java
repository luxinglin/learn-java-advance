package cn.com.gary.util;

import cn.com.gary.model.exception.BizException;

/**
 * 实体类相关工具类
 * 解决问题： 1、快速对实体的常驻字段，如：crtUser、crtHost、updUser等值快速注入
 *
 * @author Ace
 * @version 1.0
 * @date 2016年4月18日
 * @since 1.7
 */
public class EntityUtils {

    /**
     * 根据主键属性，判断主键是否值为空
     *
     * @param entity
     * @param field
     * @return 主键为空，则返回false；主键有值，返回true
     * @author 王浩彬
     * @date 2016年4月28日
     */
    public static <T> boolean isPKNotNull(T entity, String field) {
        if (!ReflectionUtils.hasField(entity, field)) {
            return false;
        }
        Object value = ReflectionUtils.getFieldValue(entity, field);
        return isPKNotNull(value);
    }

    /**
     * @param srcObj
     * @param dest
     * @param <T>
     * @return
     */
    public static <T> T copy(Object srcObj, Class<T> dest) {
        if (srcObj == null) {
            return null;
        }
        T result = null;
        try {
            result = dest.newInstance();
            ZZBeanUtils.copyProperties(srcObj, result);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * @param key
     * @return
     */
    public static boolean isPKNullOrEmpty(Object key) {
        return !isPKNotNull(key);
    }

    /**
     * 主键有值
     *
     * @param key
     * @return
     */
    public static boolean isPKNotNull(Object key) {
        if (key == null) {
            return false;
        }

        if (key instanceof Integer) {
            return ((Integer) key).intValue() > 0;
        }
        if (key instanceof Long) {
            return ((Long) key)
                    .intValue() > 0;
        }
        return !key.toString().isEmpty();
    }

    public static boolean numberValEqualZero(Object key) {
        if (key == null) {
            return false;
        }
        if (key instanceof Integer) {
            return ((Integer) key).intValue() == 0;
        }
        if (key instanceof Long) {
            return ((Long) key)
                    .intValue() == 0;
        }
        return false;
    }

    public static boolean isValNullOrLessThenOne(Object val) {
        if (val == null) {
            return true;
        }
        if (val instanceof Integer) {
            return ((Integer) val).intValue() < 1;
        }
        if (val instanceof Long) {
            return ((Long) val)
                    .intValue() < 1;
        }
        throw new BizException("判定值不是数值");
    }
}
