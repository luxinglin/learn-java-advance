package cn.com.gary.model.pojo;

import cn.com.gary.model.constants.RestConst;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author luxinglin
 * @version 1.0
 * @Description: REST请求返回对象
 * @create 2020-01-08 21:41
 **/
@ApiModel(value = "REST结果对象")
@Data
public class RestResult<T> {
    @ApiModelProperty(value = "返回码")
    private Integer code;
    @ApiModelProperty(value = "返回状态")
    private Boolean status;
    @ApiModelProperty(value = "返回消息")
    private String message;
    @ApiModelProperty(value = "返回对象")
    private T data;

    /**
     * 构造函数1，只提供成功消息
     *
     * @param message
     */
    public RestResult(String message) {
        this.code = RestConst.HttpConst.OK.getCode();
        this.message = message;
        this.status = Status.TRUE.val;
    }

    /**
     * 构造函数2,只提供数据
     *
     * @param data
     */
    public RestResult(T data) {
        this.code = RestConst.HttpConst.OK.getCode();
        this.status = Status.TRUE.val;
        this.data = data;
    }

    public RestResult(RestConst.RestEnum restEnum, Status status, String message) {
        this.code = restEnum.getCode();
        this.status = status.val;
        this.message = message;
    }

    public RestResult(Integer code, boolean status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }

    public RestResult(Integer code, boolean status, String message, T data) {
        this.code = code;
        this.status = status;
        this.message = message;
        this.data = data;
    }

    @Override
    public String toString() {
        return "R{" +
                "code=" + code +
                ", status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public enum Status {
        FAIL(false),
        TRUE(true);
        boolean val;

        Status(boolean val) {
            this.val = val;
        }
    }
}




