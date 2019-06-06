package com.zjhuang.springboot.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 过滤器
 *
 * @author zjhuang
 * @create 2019/6/4 15:21
 **/
public class SecondFilter implements Filter {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("second filter init, filter name: " + filterConfig.getFilterName());
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("second filter uri: " + ((HttpServletRequest) servletRequest).getServletPath());
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        log.info("second filter destroy");
    }
}
