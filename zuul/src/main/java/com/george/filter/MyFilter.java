package com.george.filter;

import com.alibaba.fastjson.JSON;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

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
     * @return
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
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        return null;
    }
}
