package cn.com.gary.util.date;

/**
 * @author luxinglin
 * @version 1.0
 * @Description: TODO
 * @create 2018-05-10 13:42
 **/
public enum Week {
    MONDAY("星期一", "Monday", "Mon.", 1), TUESDAY("星期二", "Tuesday", "Tues.", 2), WEDNESDAY(
            "星期三", "Wednesday", "Wed.", 3), THURSDAY("星期四", "Thursday",
            "Thur.", 4), FRIDAY("星期五", "Friday", "Fri.", 5), SATURDAY("星期六",
            "Saturday", "Sat.", 6), SUNDAY("星期日", "Sunday", "Sun.", 7);

    String cnName;
    String enName;
    String enShortName;
    int number;

    Week(String cnName, String enName, String enShortName, int number) {
        this.cnName = cnName;
        this.enName = enName;
        this.enShortName = enShortName;
        this.number = number;
    }

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getEnShortName() {
        return enShortName;
    }

    public void setEnShortName(String enShortName) {
        this.enShortName = enShortName;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}