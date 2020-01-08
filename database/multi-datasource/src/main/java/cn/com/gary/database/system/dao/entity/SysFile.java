package cn.com.gary.database.system.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 系统-附件上传表
 * </p>
 *
 * @author pai
 * @since 2019-09-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_file")
public class SysFile extends Model<SysFile> {

    private static final long serialVersionUID = 1L;

    /**
     * 文件ID
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /**
     * 文件名称
     */
    @TableField("FILE_NAME")
    private String fileName;

    /**
     * 文件原始名称
     */
    @TableField("ORIGINAL_FILE_NAME")
    private String originalFileName;

    /**
     * 上传人
     */
    @TableField("USER_ID")
    private Long userId;

    /**
     * 文件全路径
     */
    @TableField("FULL_PATH")
    private String fullPath;

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
