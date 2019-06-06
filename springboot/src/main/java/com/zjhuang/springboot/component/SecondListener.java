package com.zjhuang.springboot.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

/**
 * 监听器
 *
 * @author zjhuang
 * @create 2019/6/4 15:21
 **/
public class SecondListener implements ServletRequestListener {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
        log.info("second listener has sent response, uri: " + request.getServletPath());
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
        log.info("second listener has received request, uri: " + request.getServletPath());
    }

}