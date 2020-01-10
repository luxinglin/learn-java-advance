package cn.com.gary.database.product.dao.entity;

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
 * 
 * </p>
 *
 * @author pai
 * @since 2020-01-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("product")
public class Product extends Model<Product> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 名称
     */
    @TableField("NAME")
    private String name;

    /**
     * 价格
     */
    @TableField("PRICE")
    private Double price;

    /**
     * 创建时间
     */
    @TableField("CREATED_DT")
    private LocalDateTime createdDt;

    /**
     * 创建人
     */
    @TableField("CREATED_BY")
    private Long createdBy;

    /**
     * 更新时间
     */
    @TableField("UPDATED_DT")
    private LocalDateTime updatedDt;

    /**
     * 更新人
     */
    @TableField("UPDATED_BY")
    private Long updatedBy;

    /**
     * 版本
     */
    @TableField("VERSION")
    private Long version;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
