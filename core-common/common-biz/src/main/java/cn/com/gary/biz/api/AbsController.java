package cn.com.gary.biz.api;

import cn.com.gary.biz.token.util.TokenUtil;
import cn.com.gary.model.constants.CommonConstants;
import cn.com.gary.util.ToyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * @author luxinglin
 * @version 1.0
 * @Description: TODO
 * @create 2020-01-09 14:56
 **/
@Slf4j
public abstract class AbsController {
    @Autowired
    protected HttpServletRequest request;

    /**
     * 获取登录用户企业ID
     *
     * @return
     */
    public Long getLoginOrgId() {
        if (request.getHeader(CommonConstants.JWT_KEY_ORG_ID) != null) {
            return Long.valueOf(request.getHeader(CommonConstants.JWT_KEY_ORG_ID));
        }
        if (request.getHeader(CommonConstants.TOKEN) == null) {
            return null;
        }

        String token = getToken();
        if (ToyUtil.nullOrEmpty(token)) {
            return null;
        }

        try {
            return Long.valueOf(TokenUtil.getOrgId(token));
        } catch (Exception ex) {
            log.error("获取登录组织ID失败，{}", ex.getMessage());
        }

        return null;
    }

    /**
     * 获取登录用户ID
     *
     * @return
     */
    public Long getLoginUserId() {
        if (request.getHeader(CommonConstants.JWT_KEY_USER_ID) != null) {
            return Long.valueOf(request.getHeader(CommonConstants.JWT_KEY_USER_ID));
        }
        Long invalidId = 0L;
        if (request.getHeader(CommonConstants.TOKEN) == null) {
            return invalidId;
        }

        String token = getToken();
        if (ToyUtil.nullOrEmpty(token)) {
            return invalidId;
        }

        try {
            return Long.valueOf(TokenUtil.getUserId(token));
        } catch (Exception ex) {
            log.error("根据token获取用户id失败，{}", ex.getMessage());
        }
        return invalidId;
    }

    /**
     * 获取登录用户的token信息
     *
     * @return
     */
    public String getToken() {
        try {
            return java.net.URLDecoder.decode(String.valueOf(request.getHeader(CommonConstants.TOKEN)), CommonConstants.UFT8_CHAR_SET);
        } catch (UnsupportedEncodingException ex) {
            log.error("TOKEN解码异常,{}", ex.getMessage());
        }
        return null;
    }
}
