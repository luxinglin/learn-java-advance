package cn.com.gary.model.exception;


import cn.com.gary.model.constants.RestConst;

public class ForbiddenBizException extends RuntimeException {

    /**
     * 错误类型
     */
    private RestConst.RestEnum restEnum;

    /**
     * 构造 Biz exception 的实例.
     *
     * @param restEnum   the error enum
     * @param frdMessage the frd message
     */
    public ForbiddenBizException(RestConst.RestEnum restEnum, String frdMessage) {
        super(frdMessage);
        this.restEnum = restEnum;
    }

    /**
     * 构造 Biz exception 的实例.
     *
     * @param restEnum  the error enum
     * @param throwable the throwable
     */
    public ForbiddenBizException(RestConst.RestEnum restEnum, Throwable throwable) {
        super(throwable);
        this.restEnum = restEnum;
    }

    /**
     * 构造 Biz exception 的实例.
     *
     * @param restEnum   the error enum
     * @param frdMessage the frd message
     * @param throwable  the throwable
     */
    public ForbiddenBizException(RestConst.RestEnum restEnum, String frdMessage, Throwable throwable) {
        super(frdMessage, throwable);
        this.restEnum = restEnum;
    }

    /**
     * 构造 Biz exception 的实例.
     *
     * @param frdMessage the frd message
     */
    public ForbiddenBizException(String frdMessage) {
        super(frdMessage);
        this.restEnum = RestConst.BizError.BIZ_ERROR;
    }

    /**
     * 构造 Biz exception 的实例.
     *
     * @param throwable the throwable
     */
    public ForbiddenBizException(Throwable throwable) {
        super(throwable);
        this.restEnum = RestConst.BizError.BIZ_ERROR;
    }

    /**
     * 构造 Biz exception 的实例.
     *
     * @param frdMessage the frd message
     * @param throwable  the throwable
     */
    public ForbiddenBizException(String frdMessage, Throwable throwable) {
        super(frdMessage, throwable);
        this.restEnum = RestConst.BizError.BIZ_ERROR;
    }

    public RestConst.RestEnum getRestEnum() {
        return restEnum;
    }

    public void setRestEnum(RestConst.RestEnum restEnum) {
        this.restEnum = restEnum;
    }

}
