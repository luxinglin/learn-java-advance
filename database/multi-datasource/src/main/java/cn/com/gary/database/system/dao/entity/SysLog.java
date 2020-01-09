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
 * 系统-操作日志表
 * </p>
 *
 * @author pai
 * @since 2019-09-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_log")
public class SysLog extends Model<SysLog> {

    private static final long serialVersionUID = 1L;

    /**
     * 日志ID
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    @TableField("USER_ID")
    private Long userId;

    /**
     * 用户账号
     */
    @TableField("ACCOUNT")
    private String account;

    /**
     * 用户姓名
     */
    @TableField("REAL_NAME")
    private String realName;

    /**
     * 访问IP
     */
    @TableField("IP")
    private String ip;

    /**
     * 操作时间
     */
    @TableField("OPERATE_DT")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime operateDt;

    /**
     * 操作成功标识，Y-成功，N-失败
     */
    @TableField("SUCCESS")
    private String success;

    /**
     * 失败原因
     */
    @TableField("FAILED_REASON")
    private String failedReason;

    /**
     * 客户端类型
     */
    @TableField("CLIENT_INFO")
    private String clientInfo;

    /**
     * 操作模块
     */
    @TableField("OPERATE_MODULE")
    private String operateModule;

    /**
     * 操作描述
     */
    @TableField("OPERATE_DESC")
    private String operateDesc;

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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
