package com.george.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * <p>
 *  自定义过滤器
 * </p>
 *
 * @author GeorgeChan 2019/10/3 1:34
 * @version 1.0
 * @since jdk1.8
 */
@Component
public class MyFilter extends ZuulFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyFilter.class);
    /**
     * 过滤器类型，前置过滤器
     * @return
     */
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    /**
     * 过滤器顺序，越小越先执行
     * @return
     */
    @Override
    public int filterOrder() {
        return 4;
    }

    /**
     * 过滤器是否生效
     * RequestContext 请求的全局对象
     * @return boolean 是否过滤
     */
    @Override
    public boolean shouldFilter() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String cookie = request.getHeader("Cookie");
        String authorization = request.getHeader("Authorization");
        LOGGER.info("Cookie ====》 {}", cookie);
        LOGGER.info("Authorization ====》 {}", authorization);
        return true;
    }

    /**
     * 业务逻辑
     * 模拟token鉴权，如果Authorization为空，表示没有权限
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        // 解决返回中文乱码问题
        requestContext.getResponse().setContentType("text/html;charset=UTF-8");
        HttpServletRequest request = requestContext.getRequest();
        String cookie = request.getHeader("Cookie");
        String authorization = request.getHeader("Authorization");

        // 这里模拟权限检验，失败返回401
        if (StringUtils.isEmpty(authorization)) {
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
            requestContext.setResponseBody("权限不足！");
        }
        return null;
    }
}
