package com.zjhuang.servlet;

import com.zjhuang.annotation.SdController;
import com.zjhuang.annotation.SdService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
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
        doScanner(contextConfig.getProperty("scan.package"));
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
            Class<?> aClass = Class.forName(className);
            Object object = aClass.newInstance();
            if (aClass.isAnnotationPresent(SdController.class)) {
                String name = lowerFirstChar(aClass.getName());
                ioc.put(name, object);
            } else if (aClass.isAnnotationPresent(SdService.class)) {
                SdService sdService = aClass.getAnnotation(SdService.class);
                String value = sdService.value();
                if ("".equals(value)) {
                    String name = lowerFirstChar(aClass.getName());
                    ioc.put(name, object);
                } else {
                    ioc.put(value, object);
                }
                Class<?>[] interfaces = aClass.getInterfaces();
                for (Class<?> anInterface : interfaces) {

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
    private void doScanner(String scanPackage) {
        URL url = this.getClass().getClassLoader().getResource("/" + scanPackage.replaceAll("\\.", "/"));
        File classPathDir = new File(url.getFile());
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
