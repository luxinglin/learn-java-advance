package cn.com.gary.database.common.constant;

import lombok.Getter;

/**
 * @author luxinglin
 * @version 1.0
 * @Description: TODO
 * @create 2020-01-09 17:57
 **/
@Getter
public enum SysLogModuleEnum {
    AUTH_MGT_LOGIN("login", null, "登录");
    private String code;
    private String pCode;
    private String name;

    SysLogModuleEnum(String code, String pCode, String name) {
        this.code = code;
        this.pCode = pCode;
        this.name = name;
    }
}
