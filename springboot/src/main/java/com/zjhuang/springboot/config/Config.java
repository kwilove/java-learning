package com.zjhuang.springboot.config;

import com.zjhuang.springboot.component.SecondFilter;
import com.zjhuang.springboot.component.SecondListener;
import com.zjhuang.springboot.component.SecondServlet;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import javax.servlet.Servlet;
import java.util.EventListener;

/**
 * 全局配置类
 *
 * @author zjhuang
 * @create 2019/6/5 17:39
 **/
@Configuration
public class Config {

    /**
     * 注册一个Filter
     *
     * @return FilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean registerFilter() {
        // 创建Filter注册Bean
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
        // 创建自定义的Filter对象
        SecondFilter filter = new SecondFilter();
        // 注册Filter
        registration.setFilter(filter);
        // 设置Filter名称
        registration.setName("second_filter");
        // 设置Fliter匹配规则
        registration.addUrlPatterns("/*");
        // 设置排序，在存在多个Filter实例的情况下确定Filter的执行顺序
        registration.setOrder(2);
        return registration;
    }


    /**
     * 注册一个Servlet
     *
     * @return ServletRegistrationBean
     */
    @Bean
    public ServletRegistrationBean registerServlet() {
        // 创建Servlet注册Bean
        ServletRegistrationBean<Servlet> registration = new ServletRegistrationBean<>();
        // 创建自定义的Servlet对象
        SecondServlet servlet = new SecondServlet();
        // 注册Servlet
        registration.setServlet(servlet);
        // 设置Servlet名称
        registration.setName("second_servlet");
        // 设置Servlet配置规则
        registration.addUrlMappings("/second_servlet");
        // 设置加载参数
        registration.setLoadOnStartup(2);
        return registration;
    }

    /**
     * 注册一个Listener
     *
     * @return ServletListenerRegistrationBean
     */
    @Bean
    public ServletListenerRegistrationBean registerListener() {
        // 创建Listener注册Bean
        ServletListenerRegistrationBean<EventListener> registration = new ServletListenerRegistrationBean<>();
        // 创建自定义的Listener对象
        SecondListener listener = new SecondListener();
        // 注册Listener
        registration.setListener(listener);
        // 设置排序，在存在多个Listener实例的情况下确定Listener的执行顺序
        registration.setOrder(2);
        return registration;
    }

}
