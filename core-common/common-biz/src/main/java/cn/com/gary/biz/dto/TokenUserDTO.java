package cn.com.gary.biz.dto;

import lombok.Data;

/**
 * @author luxinglin
 * @version 1.0
 * @Description: TODO
 * @create 2019-09-18 16:03
 **/
@Data
public class TokenUserDTO implements java.io.Serializable {
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 账号
     */
    private String account;
    /**
     * 组织ID
     */
    private Long orgId;
    /**
     * 密码
     */
    private String password;
    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 性别 0:男 1:女
     */
    private Integer sex;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 用户状态（0:禁用，1:有效，2:锁定）
     */
    private String status;
    /**
     * 上次登录IP
     */
    private String lastLoginIp;
    /**
     * token
     */
    private String token;
}
