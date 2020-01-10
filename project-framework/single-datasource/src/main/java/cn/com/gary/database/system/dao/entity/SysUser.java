package cn.com.gary.database.system.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 系统-用户表
 * </p>
 *
 * @author pai
 * @since 2019-09-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_user")
public class SysUser extends Model<SysUser> {

    private static final long serialVersionUID = 1L;
    /**
     * 用户ID
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;
    /**
     * 真实姓名
     */
    @TableField("REAL_NAME")
    private String realName;
    /**
     * 账号
     */
    @TableField("ACCOUNT")
    private String account;
    /**
     * 密码
     */
    @TableField("PASSWORD")
    private String password;
    /**
     * 邮箱
     */
    @TableField("EMAIL")
    private String email;
    /**
     * 电话
     */
    @TableField("MOBILE")
    private String mobile;
    /**
     * 微信
     */
    @TableField("WE_CHAT")
    private String weChat;
    /**
     * 性别，1男，2女，null未设置
     */
    @TableField("SEX")
    private Integer sex;
    /**
     * 头像
     */
    @TableField("PHOTO")
    private String photo;
    /**
     * 地址
     */
    @TableField("ADDRESS")
    private String address;
    /**
     * 启用禁用状态,Y启用，N未启用，null未设置
     */
    @TableField("ENABLED")
    private String enabled;
    /**
     * 备注
     */
    @TableField("REMARK")
    private String remark;
    /**
     * 创建人,引用用户ID主键
     */
    @TableField("CREATED_BY")
    private Long createdBy;
    /**
     * 创建时间
     */
    @TableField("CREATED_DT")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createdDt;
    /**
     * 更新人,引用用户ID主键
     */
    @TableField("UPDATED_BY")
    private Long updatedBy;
    /**
     * 更新时间
     */
    @TableField("UPDATED_DT")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updatedDt;
    /**
     * 版本,修改一次版本号+1
     */
    @TableField("VERSION")
    private Integer version;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
