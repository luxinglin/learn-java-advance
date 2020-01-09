package cn.com.gary.util.page;

import cn.com.gary.model.exception.BizException;
import cn.com.gary.model.page.TimeRangeParam;
import cn.com.gary.util.ToyUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author luxinglin
 * @version 1.0
 * @Description: 分页查询工具类
 * @create 2019-10-15 16:04
 **/
@Slf4j
public class PageUtil {
    /**
     * SQL保留字（sql语句中的非法字符）
     */
    private final static String[] SQL_RESERVED_WORDS = new String[]{
            "master", "truncate", "insert", "select", "delete", "update", "declare", "alter", "drop"
    };

    private PageUtil() {
    }

    /**
     * 驼峰属性转数据库排序下划线字段
     *
     * @param fields 属性名数值
     * @param cls    实体类
     * @return
     */
    public static String[] getOrderColumns(String[] fields, Class cls) {
        if (fields == null || fields.length == 0) {
            return null;
        }

        List<String> columns = new ArrayList<>();
        for (String field : fields) {
            if (ToyUtil.nullOrEmpty(field)) {
                continue;
            }
            if (!validDateClazzField(cls, field)) {
                continue;
            }
            String under = ToyUtil.underScoreName(field);
            columns.add(under.toUpperCase());
        }
        String[] str = new String[columns.size()];
        return columns.toArray(str);
    }


    /**
     * 构造时间段参数
     *
     * @param cls    条件所在类
     * @param enName 字段名称
     * @param start  开始时间（日期）
     * @param end    结束时间（日期）
     * @return 时间段参数
     */
    public static TimeRangeParam constructTimeRangeParam(final Class cls, final String enName, Object start, Object end) {
        if (!validDateClazzField(cls, enName)) {
            return null;
        }

        //both null
        if (start == null && end == null) {
            return null;
        }

        TimeRangeParam timeRangeParam = new TimeRangeParam();
        timeRangeParam.setEnName(enName);
        timeRangeParam.setStart(start);
        timeRangeParam.setEnd(end);

        prepareTimeRange(timeRangeParam);
        return timeRangeParam;
    }

    /**
     * 转换前台过来的时间条件为查询sql
     *
     * @param cls             条件所在类
     * @param timeRangeParams 时间参数列表
     * @return
     */
    public static void executeTimeRangeStatement(Class cls, List<TimeRangeParam> timeRangeParams) {
        if (cls == null) {
            timeRangeParams = null;
            return;
        }

        if (ToyUtil.listEmpty(timeRangeParams)) {
            return;
        }

        Iterator<TimeRangeParam> it = timeRangeParams.iterator();
        while (it.hasNext()) {
            TimeRangeParam timeRangeParam = it.next();
            final String enName = timeRangeParam.getEnName();
            if (ToyUtil.nullOrEmpty(enName)) {
                it.remove();
            }
            if (timeRangeParam.getStart() == null && timeRangeParam.getEnd() == null) {
                it.remove();
            }
            if (!validDateClazzField(cls, enName)) {
                it.remove();
            }

            prepareTimeRange(timeRangeParam);
        }
    }


    /**
     * SQL注入过滤
     *
     * @param str 待验证的字符串
     * @author qiujiaming
     */
    public static String sqlInject(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }

        //去掉'|"|;|\字符
        str = StringUtils.replace(str, "'", "");
        str = StringUtils.replace(str, "\"", "");
        str = StringUtils.replace(str, ";", "");
        str = StringUtils.replace(str, "\\", "");


        //判断是否包含非法字符
        for (String reservedWord : SQL_RESERVED_WORDS) {
            boolean containKeyword = StringUtils.containsIgnoreCase(str, reservedWord);
            if (containKeyword) {
                throw new BizException("包含非法字符");
            }
        }

        return str;
    }

    /**
     * 判断是否是类下的日期类型字段
     *
     * @param clazz
     * @param enName
     * @return
     */
    private static boolean validDateClazzField(Class clazz, String enName) {
        if (clazz == null) {
            return false;
        }

        if (ToyUtil.nullOrEmpty(enName)) {
            return false;
        }

        Field field = ReflectionUtils.findField(clazz, enName);
        if (field == null) {
            log.warn("构造时间段参数警告，类{}下不存在{}字段，忽略该条件", clazz.getName(), enName);
            return false;
        }

        boolean typeMatch = field.getType().getName().equals(Date.class.getName())
                || field.getType().getName().equals(java.sql.Date.class.getName())
                || field.getType().getName().equals(LocalDateTime.class.getName())
                || field.getType().getName().equals(LocalDate.class.getName());
        if (!typeMatch) {
            log.warn("构造时间段参数警告，类{}下{}字段是{}，不属于合规的时间类型，忽略该条件", clazz.getName(), enName, field.getType().getName());
            return false;
        }

        return true;
    }

    /**
     * 构造时间段参数过来条件语句
     *
     * @param timeRangeParam
     */
    private static void prepareTimeRange(TimeRangeParam timeRangeParam) {
        if (timeRangeParam == null) {
            return;
        }

        String fieldName = ToyUtil.underScoreName(timeRangeParam.getEnName()).toUpperCase();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(sqlInject(fieldName));

        boolean hasBoth = timeRangeParam.getStart() != null && timeRangeParam.getEnd() != null;
        if (hasBoth) {
            stringBuffer.append(" BETWEEN #{item.start} AND #{item.end}");
        } else {
            if (timeRangeParam.getStart() != null) {
                stringBuffer.append(" >= #{item.start}");
            } else {
                stringBuffer.append(" < #{item.end}");
            }
        }

        timeRangeParam.setStatement(stringBuffer.toString());
    }
}
