package cn.com.gary.biz.util;

import cn.com.gary.biz.token.util.TokenUtil;
import cn.com.gary.model.constants.CommonConstants;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * @author luxinglin
 * @version 1.0
 * @Description: TODO
 * @create 2018-08-16 11:52
 **/
@Slf4j
public class RequestUtil {
    /**
     * 从请求对象中，获取登录用户所在的组织id信息
     *
     * @param request
     * @return
     */
    public static Long getLoginOrgId(HttpServletRequest request) {
        if (request.getHeader(CommonConstants.JWT_KEY_ORG_ID) != null) {
            return Long.valueOf(request.getHeader(CommonConstants.JWT_KEY_ORG_ID));
        }
        if (request.getHeader(CommonConstants.TOKEN) == null) {
            return null;
        }
        try {
            return Long.valueOf(TokenUtil.getOrgId(java.net.URLDecoder.decode(String.valueOf(request.getHeader(CommonConstants.TOKEN)),
                    CommonConstants.UFT8_CHAR_SET)));
        } catch (UnsupportedEncodingException e) {
            log.error("获取组织ID失败");
        }
        return null;
    }

    /**
     * 从请求对象中，获取登录用户token信息
     *
     * @param request
     * @return
     */
    public static String getLoginToken(HttpServletRequest request) {
        try {
            return java.net.URLDecoder.decode(String.valueOf(request.getHeader(CommonConstants.TOKEN)),
                    CommonConstants.UFT8_CHAR_SET);
        } catch (UnsupportedEncodingException ex) {
            log.error("TOKEN解码异常,{}", ex.getMessage());
        }
        return null;
    }

    /**
     * 从请求对象中，获取登录用户的id信息
     *
     * @param request
     * @return
     */
    public static Long getLoginUserId(HttpServletRequest request) {
        if (request.getHeader(CommonConstants.JWT_KEY_USER_ID) != null) {
            return Long.valueOf(request.getHeader(CommonConstants.JWT_KEY_USER_ID));
        }
        if (request.getHeader(CommonConstants.TOKEN) == null) {
            return 0L;
        }
        try {
            return Long.valueOf(TokenUtil.getUserId(java.net.URLDecoder.decode(String.valueOf(request.getHeader(CommonConstants.TOKEN)),
                    CommonConstants.UFT8_CHAR_SET)));
        } catch (UnsupportedEncodingException e) {
            log.error("获取登录用户ID失败");
        }
        return 0L;
    }

}