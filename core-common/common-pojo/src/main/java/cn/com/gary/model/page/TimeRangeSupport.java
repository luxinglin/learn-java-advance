package cn.com.gary.model.page;

import java.util.List;

/**
 * @author luxinglin
 * @version 1.0
 * @Description: TODO
 * @create 2019-10-30 12:16
 **/
public abstract class TimeRangeSupport {
    /**
     * 时间段参数
     */
    List<TimeRangeParam> timeRangeParams;

    public List<TimeRangeParam> getTimeRangeParams() {
        return timeRangeParams;
    }

    public void setTimeRangeParams(List<TimeRangeParam> timeRangeParams) {
        this.timeRangeParams = timeRangeParams;
    }
}
