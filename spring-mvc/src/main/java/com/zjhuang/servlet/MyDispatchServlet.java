package com.zjhuang.servlet;

import com.zjhuang.annotation.MyController;
import com.zjhuang.annotation.MyService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

/**
 * 自定义实现的核心转发器
 *
 * @author zjhuang
 * @create 2018/8/2 10:20
 **/
public class MyDispatchServlet extends HttpServlet {
    /**
     * spring配置对象
     */
    private Properties contextConfig = new Properties();
    /**
     * 扫描指定包下所有类的className
     */
    private List<String> classNameList = new ArrayList<>();
    /**
     * Spring IOC容器
     */
    private HashMap<String, Object> ioc = new HashMap<>();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 6、执行doDispatcher
        doDispatcher(req, resp);
    }

    private void doDispatcher(HttpServletRequest req, HttpServletResponse resp) {
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        // 1、加载配置文件
        doConfig(config.getInitParameter("contextConfiguration"));
        // 2、扫描所有相关类
        try {
            doScanner(contextConfig.getProperty("scan.package"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // 3、初始化刚才扫描到的类，并存入IOC容器中
        try {
            doInject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        // 4、自动注入

        // 5、初始化handlerMapping，并交由spring管理

    }

    /**
     * 依赖注入
     *
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private void doInject() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        if (classNameList.isEmpty()) {
            return;
        }
        for (String className : classNameList) {
            Class<?> clazz = Class.forName(className);
            if (clazz.isAnnotation() || clazz.isInterface()) {
                continue;
            }
            Object object = clazz.newInstance();
            if (clazz.isAnnotationPresent(MyController.class)) {
                String name = lowerFirstChar(clazz.getSimpleName());
                ioc.put(name, object);
            } else if (clazz.isAnnotationPresent(MyService.class)) {
                MyService myService = clazz.getAnnotation(MyService.class);
                String value = myService.value();
                if ("".equals(value)) {
                    String name = lowerFirstChar(clazz.getSimpleName());
                    ioc.put(name, object);
                } else {
                    ioc.put(value, object);
                }
                Class<?>[] interfaces = clazz.getInterfaces();
                for (Class<?> anInterface : interfaces) {
                    String name = lowerFirstChar(anInterface.getSimpleName());
                    ioc.put(name, object);
                }
            }
        }
    }

    /**
     * 字符串首字母小写
     *
     * @param className
     * @return
     */
    private String lowerFirstChar(String className) {
        char[] chars = className.toCharArray();
        chars[0] += 32;
        return String.copyValueOf(chars);
    }

    /**
     * 加载配置
     *
     * @param contextConfiguration
     */
    private void doConfig(String contextConfiguration) {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(contextConfiguration);
        try {
            contextConfig.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 扫描相关包
     *
     * @param scanPackage
     */
    private void doScanner(String scanPackage) throws UnsupportedEncodingException {
        URL url = this.getClass().getClassLoader().getResource("/" + scanPackage.replaceAll("\\.", "/"));
        String filePath = URLDecoder.decode(url.getFile(), "UTF8");
        File classPathDir = new File(filePath);
        for (File file : classPathDir.listFiles()) {
            if (file.isDirectory()) {
                doScanner(scanPackage + "." + file.getName());
                continue;
            }
            String className = scanPackage + "." + file.getName().replace(".class", "");
            classNameList.add(className);
        }
    }


}
