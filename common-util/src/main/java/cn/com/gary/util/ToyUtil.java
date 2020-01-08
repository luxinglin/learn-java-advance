package cn.com.gary.util;

import cn.com.gary.model.constants.CommonConstants;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author luxinglin
 * @version 1.0
 * @Description: TODO
 * @create 2018-04-18 14:12
 **/
public class ToyUtil {

    /**
     * 正则表达式：验证汉字
     */
    public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$";
    /**
     * 正则表达式：验证身份证
     */
    public static final String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";
    /**
     * 正则表达式：验证URL
     */
    public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
    /**
     * 正则表达式：验证IP地址
     */
    public static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";
    /**
     * 邮箱正则校验
     */
    private final static String EMAIL_REG = "[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?";
    private static List<String> MOBILE_REGS;

    /**
     * 手机号正则校验
     */
    static {
        MOBILE_REGS = new ArrayList<>();
        //移动
        MOBILE_REGS.add("^((13[4-9])|(147)|(15[0-2,7-9])|(178)|(18[2-4,7-8]))\\d{8}|(1705)\\d{7}$");
        //联通
        MOBILE_REGS.add("^((13[0-2])|(145)|(15[5-6])|(176)|(18[5,6]))\\d{8}|(1709)\\d{7}$");
        //电信
        MOBILE_REGS.add("^((133)|(153)|(177)|(18[0,1,9])|(149)|(199))\\d{8}$");
        //虚拟运营商
        MOBILE_REGS.add("^((170))\\d{8}|(1718)|(1719)\\d{7}$");
    }

    private ToyUtil() {
    }

    /**
     * 判断id值是否为>0的数值
     *
     * @param id
     * @return
     */
    public static boolean gtZero(Object id) {
        if (id == null) {
            return false;
        }
        if (id instanceof Integer) {
            return ((Integer) id).intValue() > 0;
        }
        if (id instanceof Long) {
            return ((Long) id).intValue() > 0;
        }
        return false;
    }

    public static String appendSqlLike(String condition) {
        if (nullOrEmpty(condition)) {
            return "%";
        }
        return "%".concat(condition.trim()).concat("%");
    }

    /**
     * @param str
     * @return
     */
    public static boolean nullOrEmpty(String str) {
        return str == null || str.isEmpty() || "null".equals(str);
    }

    public static boolean notEmpty(String str) {
        return !nullOrEmpty(str);
    }

    public static String getObjectValue(Object obj) {
        return obj == null ? "" : obj.toString();
    }

    /**
     * 校验boolean值是否合法
     *
     * @param val
     * @return
     */
    public static boolean validBoolean(String val) {
        return CommonConstants.BOOLEAN_TRUE.equals(val) ||
                CommonConstants.BOOLEAN_FALSE.equals(val);
    }

    /**
     * @param list
     * @return
     */
    public static boolean listEmpty(List list) {
        return list == null || list.size() == 0;
    }

    /**
     * @param list
     * @return
     */
    public static boolean listNotEmpty(List list) {
        return !listEmpty(list);
    }

    public static boolean listNull(List list) {
        return list == null;
    }

    public static void setBeanProperty(Object bean, String property, Object value) throws Throwable {

        try {
            PropertyUtils.setProperty(bean, property, value);
        } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw e;
        }
    }

    public static Class getBeanPropertyType(Object bean, String property) throws Throwable {
        try {
            Class type = null;
            if (bean instanceof DynaBean) {
                DynaProperty descriptor = ((DynaBean) bean).getDynaClass().getDynaProperty(property);
                if (descriptor != null) {
                    type = descriptor.getType();
                }
            } else {
                type = PropertyUtils.getPropertyType(bean, property);
            }
            return type;
        } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw e;
        }
    }

    public static Object getBeanProperty(Object bean, String property) throws Throwable {
        Object value = null;
        try {
            value = PropertyUtils.getProperty(bean, property);
        } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw e;
        }
        return value;
    }

    /**
     * 合法手机号校验
     *
     * @param mobile 手机号
     * @return
     */
    public static boolean isMobile(final String mobile) {
        if (nullOrEmpty(mobile)) {
            return false;
        }
        for (String reg : MOBILE_REGS) {
            Pattern pattern = Pattern.compile(reg);
            Matcher matcher = pattern.matcher(mobile);
            if (matcher.matches()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 合法邮箱地址校验
     *
     * @param email 邮箱地址
     * @return
     */
    public static boolean isEmail(final String email) {
        if (nullOrEmpty(email)) {
            return false;
        }
        Pattern pattern = Pattern.compile(EMAIL_REG);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    /**
     * 校验汉字
     *
     * @param chinese
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isChinese(String chinese) {
        if (nullOrEmpty(chinese)) {
            return false;
        }
        return Pattern.matches(REGEX_CHINESE, chinese);
    }

    /**
     * 校验身份证
     *
     * @param idCard
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isIDCard(String idCard) {
        if (nullOrEmpty(idCard)) {
            return false;
        }
        return Pattern.matches(REGEX_ID_CARD, idCard);
    }

    /**
     * 校验URL
     *
     * @param url
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUrl(String url) {
        if (nullOrEmpty(url)) {
            return false;
        }
        return Pattern.matches(REGEX_URL, url);
    }

    /**
     * 校验IP地址
     *
     * @param ipAddr IP地址
     * @return
     */
    public static boolean isIPAddr(String ipAddr) {
        if (nullOrEmpty(ipAddr)) {
            return false;
        }
        return Pattern.matches(REGEX_IP_ADDR, ipAddr);
    }

    /**
     * 转换为下划线
     *
     * @param camelCaseName
     * @return
     */
    public static String underScoreName(String camelCaseName) {
        StringBuilder result = new StringBuilder();
        if (nullOrEmpty(camelCaseName)) {
            return result.toString();
        }

        result.append(camelCaseName.substring(0, 1).toLowerCase());
        for (int i = 1; i < camelCaseName.length(); i++) {
            char ch = camelCaseName.charAt(i);
            if (Character.isUpperCase(ch)) {
                result.append("_");
                result.append(Character.toLowerCase(ch));
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }

    /**
     * 转换为驼峰
     *
     * @param underscoreName
     * @return
     */
    public static String camelCaseName(String underscoreName) {
        StringBuilder result = new StringBuilder();
        if (nullOrEmpty(underscoreName)) {
            return result.toString();
        }

        boolean flag = false;
        for (int i = 0; i < underscoreName.length(); i++) {
            char ch = underscoreName.charAt(i);
            if ("_".charAt(0) == ch) {
                flag = true;
            } else {
                if (flag) {
                    result.append(Character.toUpperCase(ch));
                    flag = false;
                } else {
                    result.append(ch);
                }
            }
        }
        return result.toString();
    }


    /**
     * Tests if 2 strings are equal, but given they are not null
     *
     * @param string1
     * @param string2
     * @return
     */
    public static boolean isNotNullAndEquals(String string1, String string2) {
        if (string1 == null) {
            return false;
        } else {
            return string1.equals(string2);
        }
    }

    public static boolean equals(String string1, String string2) {
        return StringUtils.equals(string1, string2);
    }

}
