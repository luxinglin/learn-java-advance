package cn.com.gary.biz.token.util;

import cn.com.gary.biz.token.model.ValidResult;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

@Slf4j
public class TokenUtil {
    public static final String USER_ID = "USER_ID";
    public static final String ORG_ID = "ORG_ID";
    public static final String IP = "IP";
    final static String UN_KNOW = "unknown";
    protected static Logger logger = LoggerFactory.getLogger(TokenUtil.class);

    /**
     * token generator
     *
     * @param tokenParam
     * @return
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws IOException
     */
    public static String genToken(Map<String, String> tokenParam) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException {
        return Des3Util.genToken(tokenParam.get(USER_ID), tokenParam.get(ORG_ID), tokenParam.get(IP));
    }

    /**
     * token valid
     *
     * @param token
     * @param userId
     * @param orgIdId
     * @param ip
     * @return
     */
    public static ValidResult validateToken(String token, String userId, String orgIdId, String ip) {
        ValidResult validResult = new ValidResult();
        validResult.setValid(false);
        validResult.setErrorMsg("token验证未知错误!");

        String decodeToken = "";
        try {
            decodeToken = decodeToken(token);
        } catch (Throwable t) {
            validResult.setErrorMsg("token无法解析!");
            return validResult;
        }
        String decodeUserId = decodeToken.substring(6, decodeToken.indexOf("</user>"));
        String decodeCompanyId = decodeToken.substring(decodeToken.indexOf("<orgId>") + 7, decodeToken.indexOf("</orgId>"));
        String decodeIp = decodeToken.substring(decodeToken.indexOf("<ip>") + 4, decodeToken.indexOf("</ip>"));
        if (userId.toUpperCase().equalsIgnoreCase(decodeUserId) && orgIdId.toUpperCase().equalsIgnoreCase(decodeCompanyId) && ip.equalsIgnoreCase(decodeIp)) {
            validResult.setValid(true);
            validResult.setErrorMsg("验证成功!");
        } else {
            validResult.setErrorMsg("无效的Token!");
        }
        return validResult;
    }

    public static ValidResult validateToken(String token, String ip) {
        ValidResult validResult = new ValidResult();
        validResult.setValid(false);
        validResult.setErrorMsg("token验证未知错误!");

        String decodeToken = "";
        try {
            decodeToken = decodeToken(token);
        } catch (Throwable t) {
            validResult.setErrorMsg("token无法解析!");
            return validResult;
        }
        String decodeIp = decodeToken.substring(decodeToken.indexOf("<ip>") + 4, decodeToken.indexOf("</ip>"));
        if (ip.equalsIgnoreCase(decodeIp)) {
            validResult.setValid(true);
            validResult.setErrorMsg("验证成功!");
        } else {
            logger.error("ip:" + ip + " tokeIp:" + decodeIp);
            validResult.setErrorMsg("无效的Token!");
        }
        return validResult;
    }

    public static ValidResult validateToken(String token) {
        ValidResult validResult = new ValidResult();
        validResult.setValid(false);
        validResult.setErrorMsg("token验证未知错误!");

        try {
            decodeToken(token);
        } catch (Throwable t) {
            validResult.setErrorMsg("token无法解析!");
            return validResult;
        }
        validResult.setValid(true);
        validResult.setErrorMsg("验证成功!");

        return validResult;
    }

    public static String getUserId(String token) {

        String decodeToken = "";
        try {
            decodeToken = decodeToken(token);
        } catch (Throwable t) {
            return null;
        }
        return decodeToken.substring(6, decodeToken.indexOf("</user>"));
    }

    public static String getOrgId(String token) {
        String decodeToken = "";
        try {
            decodeToken = decodeToken(token);
        } catch (Throwable t) {
            return null;
        }
        return decodeToken.substring(decodeToken.indexOf("<orgId>") + 7, decodeToken.indexOf("</orgId>"));
    }

    /**
     * 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址
     *
     * @param request
     * @return
     */
    public final static String getIpAddress(HttpServletRequest request) {

        final int ipLen = 15;
        String ip = request.getHeader("X-Forwarded-For");
        log.debug("****begin client ip test****");
        log.debug("getIpAddress(HttpServletRequest) - X-Forwarded-For - String ip=" + ip);

        if (ip == null || ip.length() == 0 || UN_KNOW.equalsIgnoreCase(ip)) {
            if (ip == null || ip.length() == 0 || UN_KNOW.equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
                log.debug("getIpAddress(HttpServletRequest) - Proxy-Client-IP - String ip=" + ip);
            }
            if (ip == null || ip.length() == 0 || UN_KNOW.equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
                log.debug("getIpAddress(HttpServletRequest) - WL-Proxy-Client-IP - String ip=" + ip);
            }
            if (ip == null || ip.length() == 0 || UN_KNOW.equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
                log.debug("getIpAddress(HttpServletRequest) - HTTP_CLIENT_IP - String ip=" + ip);
            }
            if (ip == null || ip.length() == 0 || UN_KNOW.equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
                log.debug("getIpAddress(HttpServletRequest) - HTTP_X_FORWARDED_FOR - String ip=" + ip);
            }
            if (ip == null || ip.length() == 0 || UN_KNOW.equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
                log.debug("getIpAddress(HttpServletRequest) - getRemoteAddr - String ip=" + ip);
            }
        } else if (ip.length() > ipLen) {
            String[] ips = ip.split(",");
            for (int index = 0; index < ips.length; index++) {
                String strIp = ips[index];
                if (!(UN_KNOW.equalsIgnoreCase(strIp))) {
                    ip = strIp;
                    break;
                }
            }
        }
        log.debug("****end client ip test**** ip = " + ip);
        return ip;
    }

    protected static String decodeToken(String token) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException {
        return Des3Util.decodeToken(token);
    }

    public static void main(String[] args) {
        try {
//		Map<String,String> map=new HashMap<String,String>();
//		map.put("USER_ID", "1");
//		map.put("COMPANY_ID", "1");
//		String token=genToken(map);
//		System.out.println(token);

            ValidResult validResult = validateToken("px5u7vYQoktC1bnkMCQa4HKOBY0Uz81gVcdxMSZeE8Gn//SF03NRWtGFVvOrt8cG6y8pL3W1J5s0\nCFFoRrD9Iw==", "127.0.0.1");
            System.out.println(validResult.isValid() + validResult.getErrorMsg());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
