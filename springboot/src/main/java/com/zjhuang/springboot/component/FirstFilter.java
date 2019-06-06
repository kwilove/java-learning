package com.zjhuang.springboot.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 过滤器
 *
 * @author zjhuang
 * @create 2019/6/4 15:21
 **/
@WebFilter(filterName = "first_filter", urlPatterns = {"/*"})
public class FirstFilter implements Filter {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("first filter init, filter name: " + filterConfig.getFilterName());
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("first filter uri: " + ((HttpServletRequest) servletRequest).getServletPath());
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        log.info("first filter destroy");
    }
}
