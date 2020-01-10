package cn.com.gary.model.page;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author luxinglin
 * @version 1.0
 * @Description: 时间段参数
 * @create 2019-10-30 10:10
 **/
@Data
public class TimeRangeParam {
    /**
     * java属性名称
     */
    private String enName;
    /**
     * 数据表字段名
     */
    private String filedName;
    /**
     * 开始时间
     */
    private Object start;
    /**
     * 结束时间
     */
    private Object end;
    /**
     * sql条件语句
     */
    private String statement;

    @Override
    public String toString() {
        return "TimeRangeParam{" +
                "enName='" + enName + '\'' +
                ", filedName='" + filedName + '\'' +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
