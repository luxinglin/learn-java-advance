package cn.com.gary.biz.handler.exception;


import cn.com.gary.model.constants.MessageConstants;
import cn.com.gary.model.constants.RestConst;
import cn.com.gary.model.exception.BizException;
import cn.com.gary.model.pojo.RestResult;
import cn.com.gary.util.web.WebUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.reflect.Field;


/**
 * Rest exception handler 类.
 * 统一异常Handle，对外唯一异常出口
 *
 * @author luxinglin
 */
@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    public RestResult handleOtherExceptions(final Exception ex) {
        log.error("RestExceptionHandler other error: {}", ex);
        RestResult tResult = new RestResult(RestConst.SysError.SYS_ERROR, RestResult.Status.FAIL, "系统错误，请联系管理员。");
        return tResult;
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public RestResult handleMethodArgumentNotValidException(final MethodArgumentNotValidException ex) {
        log.error("RestExceptionHandler MethodArgumentNotValidException error: {}", ex);
        String msgId = ex.getBindingResult().getFieldError().getDefaultMessage();

        try {
            Class cls = ex.getBindingResult().getTarget().getClass();
            String name = ex.getBindingResult().getFieldError().getField();
            Field field = ReflectionUtils.findField(cls, name);
            ApiModelProperty annotation = field.getAnnotation(ApiModelProperty.class);

            FieldError fieldError = ex.getBindingResult().getFieldError();
            if (fieldError.getCode().equalsIgnoreCase(javax.validation.constraints.NotNull.class.getSimpleName()) ||
                    fieldError.getCode().equalsIgnoreCase(javax.validation.constraints.NotEmpty.class.getSimpleName()) ||
                    fieldError.getCode().equalsIgnoreCase(javax.validation.constraints.NotBlank.class.getSimpleName())
            ) {
                msgId = MessageConstants.FIELD_VALID_NOT_NULL;
                if (annotation != null) {
                    msgId = WebUtil.getMessage(msgId, new Object[]{annotation.value()});
                }
            } else if (fieldError.getCode().equalsIgnoreCase(javax.validation.constraints.Max.class.getSimpleName())) {
                Long max = (Long) fieldError.getArguments()[1];
                msgId = MessageConstants.FIELD_VALID_MAX;
                if (annotation != null) {
                    msgId = WebUtil.getMessage(msgId, new Object[]{annotation.value(), max});
                }
            } else if (fieldError.getCode().equalsIgnoreCase(javax.validation.constraints.Min.class.getSimpleName())) {
                Long min = (Long) fieldError.getArguments()[1];
                msgId = MessageConstants.FIELD_VALID_MIN;
                if (annotation != null) {
                    msgId = WebUtil.getMessage(msgId, new Object[]{annotation.value(), min});
                }
            } else if (fieldError.getCode().equalsIgnoreCase(javax.validation.constraints.Size.class.getSimpleName())) {
                int max = (Integer) fieldError.getArguments()[1];
                int min = (Integer) fieldError.getArguments()[2];
                msgId = MessageConstants.FIELD_VALID_SIZE;
                if (annotation != null) {
                    msgId = WebUtil.getMessage(msgId, new Object[]{annotation.value(), min, max});
                }
            } else if (fieldError.getCode().equalsIgnoreCase(javax.validation.constraints.Email.class.getSimpleName())) {
                msgId = MessageConstants.FIELD_VALID_EMAIL;
                if (annotation != null) {
                    msgId = WebUtil.getMessage(msgId, new Object[]{annotation.value()});
                }
            } else {
                if (annotation != null) {
                    msgId = WebUtil.getMessage(msgId, new Object[]{annotation.value()});
                }
            }
        } catch (Exception exception) {
            log.warn("get model property 2 enhance tips warning, detail: {}", exception.getMessage());
        }
        return new RestResult(RestConst.SysError.ARGUMENT_ERROR, RestResult.Status.FAIL,
                msgId);
    }

    @ExceptionHandler(value = {BizException.class})
    public RestResult handleBizExceptions(final BizException ex) {
        log.error("RestExceptionHandler biz error: {}", ex);
        RestResult tResult = new RestResult(BizException.class.cast(ex).getRestEnum(), RestResult.Status.FAIL, ex.getMessage());
        return tResult;
    }
}

