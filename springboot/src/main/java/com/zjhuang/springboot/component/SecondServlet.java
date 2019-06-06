package com.zjhuang.springboot.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 自定义Servlet
 *
 * @author zjhuang
 * @create 2019/6/4 17:13
 **/
public class SecondServlet extends HttpServlet {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        log.info("second servlet init, servlet name: " + servletConfig.getServletName());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("second servlet uri: " + req.getServletPath());
        PrintWriter out = resp.getWriter();
        out.write("second servlet is running");
        out.flush();
    }

    @Override
    public void destroy() {
        log.info("second servlet destroy");
    }
}
