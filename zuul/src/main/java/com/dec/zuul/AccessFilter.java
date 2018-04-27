package com.dec.zuul;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Decimon
 * @date 2018/4/27
 */
public class AccessFilter extends ZuulFilter {
    @Override
    public String filterType() {
        /*
         *pre：可以在请求被路由之前调用
         * route：在路由请求时候被调用
         * post：在route和error过滤器之后被调用
         * error：处理请求时发生错误时被调用
         */
        return "pre";
    }

    @Override
    public int filterOrder() {
        // 优先级为0，数字越大，优先级越低
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        // 是否执行该过滤器，为true，说明需要过滤
        return true;
    }

    @Override
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String msg = request.getParameter("msg");
        if (StringUtils.isEmpty(msg)) {
            // 对该请求进行路由
            context.setSendZuulResponse(true);
            //返回状态码
            context.setResponseStatusCode(404);
            //返回错误信息
            context.setResponseBody("error");
        }
        return null;
    }
}
