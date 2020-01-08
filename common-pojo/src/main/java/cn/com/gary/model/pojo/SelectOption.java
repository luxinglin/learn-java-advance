package cn.com.gary.model.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author pai
 */
@ApiModel(value = "通用下拉框")
@Data
public class SelectOption<T> {
    @ApiModelProperty(value = "文本")
    private String label;
    @ApiModelProperty(value = "值")
    private Object value;
    @ApiModelProperty(value = "实际数据")
    private T data;
}
