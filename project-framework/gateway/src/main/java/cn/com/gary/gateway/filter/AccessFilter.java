package cn.com.gary.gateway.filter;

import cn.com.gary.biz.dto.TokenUserDTO;
import cn.com.gary.biz.token.model.ValidResult;
import cn.com.gary.biz.token.service.TokenJedisCache;
import cn.com.gary.biz.token.util.TokenUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author luxinglin
 */
public class AccessFilter extends ZuulFilter {

    private static Logger logger = LoggerFactory.getLogger(AccessFilter.class);
    /**
     * 不需要token允许访问的资源列表
     */
    private static List<String> allowedResources = new ArrayList<String>();
    private static List<String> allowedStaticResources = new ArrayList<String>();

    static {
        allowedStaticResources.add(".html");
        allowedStaticResources.add(".htm");
        allowedStaticResources.add(".js");
        allowedStaticResources.add(".css");
        allowedStaticResources.add(".jpg");
        allowedStaticResources.add(".png");
        allowedStaticResources.add(".gif");
        allowedStaticResources.add(".jpeg");
        allowedStaticResources.add(".bpm");
        allowedStaticResources.add(".pcx");
        allowedStaticResources.add(".mp3");
        allowedStaticResources.add(".mp4");

        /**
         * grafana静态文件
         */
        allowedStaticResources.add(".ogg");
        allowedStaticResources.add(".svg");
        allowedStaticResources.add(".ttf");
        allowedStaticResources.add(".woff2");
    }

    @Autowired
    private TokenJedisCache tokenJedisCache;
    @Value("${token.redis.timeout}")
    private int tokeRedisTimeout;

    public AccessFilter(String ignoreUrls) {
        super();
        if (!StringUtils.isEmpty(ignoreUrls)) {
            String[] urls = ignoreUrls.split(",");
            for (String url : urls) {
                allowedResources.add(url);
            }
        }
    }

    /**
     * 资源是否允许访问
     *
     * @param url
     */
    private static boolean isAllowed(String url) {
        PathMatcher matcher = new AntPathMatcher();
        for (String s : allowedResources) {
            if (matcher.match(s, url)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isStaticFile(String url) {
        for (String s : allowedStaticResources) {
            if (url.toLowerCase().contains(s)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String ip = TokenUtil.getIpAddress(request);
        try {
            String url = request.getRequestURI();
            logger.info("AccessFilter request url:{}", url);
            //gateway统一认证
            if (isAllowed(url) || isStaticFile(url)) {
                ctx.setSendZuulResponse(true);
                ctx.setResponseStatusCode(200);
                ctx.set("isSuccess", true);
                return null;
            } else {
                boolean validFlag = false;

                //从请求头获得token信息
                String token = request.getHeader("token");

                //从cookies获得token信息
                if (StringUtils.isEmpty(token)) {
                    Cookie[] cookies = request.getCookies();
                    for (Cookie cookie : cookies) {
                        String name = cookie.getName();
                        if ("access-token".equals(name)) {
                            token = cookie.getValue();
                        }
                    }
                }

                if (StringUtils.isEmpty(token)) {
                    token = request.getParameter("token");
                    logger.info("AccessFilter request param >>>>>>>>>>>>>token:{} ", token);
                } else {
                    token = java.net.URLDecoder.decode(token, "UTF-8");
                    logger.info("AccessFilter header >>>>>>>>>>>>>>>>>>>>token:{} ", token);
                }

                if (!StringUtils.isEmpty(token)) {

                    ValidResult validResult = TokenUtil.validateToken(token, ip);
                    if (validResult.isValid()) {
                        //缓存取token验证
                        TokenUserDTO redisTokenUser = tokenJedisCache.getToken(token);
                        if (redisTokenUser == null) {
                            logger.error("AccessFilter redis token is null, token:{} ", token);
                        } else {
                            boolean matchLastIp = ip.equals(redisTokenUser.getLastLoginIp());
                            boolean ipMatchLocal = "127.0.0.1".equals(ip) && "0:0:0:0:0:0:0:1".equals(redisTokenUser.getLastLoginIp());
                            boolean lastIpMatchLocal = ("0:0:0:0:0:0:0:1".equals(ip) && "127.0.0.1".equals(redisTokenUser.getLastLoginIp()));
                            if (matchLastIp || ipMatchLocal || lastIpMatchLocal) {
                                validFlag = true;
                                //重设redis过期时间
                                tokenJedisCache.expireToken(token, tokeRedisTimeout);
                            } else {
                                logger.error("AccessFilter token check failed, token:{}, ip:{}, cacheOrgId:{}, cacheUserId:{}, cacheUserIp:{}",
                                        token, ip, redisTokenUser.getOrgId(), redisTokenUser.getUserId(), redisTokenUser.getLastLoginIp());
                            }
                        }
                    } else {
                        logger.error("AccessFilter token valid failed, token:{}, error:{}", token, validResult.getErrorMsg());
                    }
                }
                ///cookieFlag = true;

                //开发测试使用
                //validFlag = true;

                if (!validFlag) {
                    //跳转到统一登录界面
                    //第三方系统调转到子系统登录界面, TODO 登录链接map放到缓存
                    ///if(url.contains("/wechat/salary/")){
                    ///	    response.sendRedirect("/wechat/salary/login");
                    ///}else{
                    ///	    response.sendRedirect("/gateway/tologin?redirect="+url);
                    ///}

                    logger.error("AccessFilter access token is error");
                    // 过滤该请求，不对其进行路由
                    ctx.setSendZuulResponse(false);
                    // 返回错误码
                    ctx.setResponseStatusCode(401);
                    // 返回错误内容
                    ctx.setResponseBody("{\"tokenFlag\":" + validFlag + ",\"result\":\"access token is error!\"}");
                    ctx.set("isSuccess", false);
                    return null;
                } else {
                    ctx.addZuulRequestHeader("userId", TokenUtil.getUserId(token));
                    ctx.addZuulRequestHeader("companyId", TokenUtil.getOrgId(token));
                    ctx.setSendZuulResponse(true);
                    ctx.setResponseStatusCode(200);
                    ctx.set("isSuccess", true);
                    logger.info("AccessFilter access token ok");
                    return null;
                }
            }
        } catch (Exception e) {
            logger.error("AccessFilter access token exception, {}", e.getMessage());
        }
        return null;
    }

}