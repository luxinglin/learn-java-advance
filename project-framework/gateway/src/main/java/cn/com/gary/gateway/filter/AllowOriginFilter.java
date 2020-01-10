package cn.com.gary.gateway.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理跨域问题
 *
 * @author lr
 */
public class AllowOriginFilter implements Filter {

    @Override
    public void destroy() {

    }

    /**
     * 跨域需要获取用户的cookie等数据，Access-Control-Allow-Origin 的值不能是*,需要指定域名才可以，这块以后可以扩展做成白名单形式；
     * 使用httpServletRequest.getHeader("origin")，支持当前访问连接跨域访问
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        //允许获取用户认证信息
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpServletResponse.setHeader("Access-Control-Allow-Origin", httpServletRequest.getHeader("origin"));
        ///httpServletResponse.setHeader("Access-Control-Allow-Origin","*");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE,OPTIONS");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type,mark,token");
        httpServletResponse.setHeader("X-CSRF-TOKEN", httpServletRequest.getSession().getId());
        ///httpServletResponse.setHeader("X-Frame-Options", "SAMEORIGIN");
        if ("OPTIONS".equals(httpServletRequest.getMethod())) {
            //options 校验是否允许跨域相关操作，直接返回200
            httpServletResponse.setStatus(200);
        } else {
            chain.doFilter(request, response);
        }

    }

    @Override
    public void init(FilterConfig arg0) {

    }

}
