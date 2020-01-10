package cn.com.gary.database.common.dto.system;

import cn.com.gary.model.page.TimeRangeSupport;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author luxinglin
 * @version 1.0
 * @Description: TODO
 * @create 2020-01-09 17:18
 **/
@Data
@JsonIgnoreProperties(value = "timeRangeParams")
public class ProductDTO extends TimeRangeSupport {
    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 价格
     */
    private Double price;

    /**
     * 创建时间
     */
    private LocalDateTime createdDt;

    /**
     * 创建人
     */
    private Long createdBy;

    /**
     * 更新时间
     */
    private LocalDateTime updatedDt;

    /**
     * 更新人
     */
    private Long updatedBy;

    /**
     * 版本
     */
    private Long version;

}
