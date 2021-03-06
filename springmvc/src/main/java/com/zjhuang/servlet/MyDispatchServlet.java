package com.zjhuang.servlet;

import com.zjhuang.modelandview.MyModelAndView;
import com.zjhuang.springmvc.annotation.*;
import com.zjhuang.utils.JavassistUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import javassist.NotFoundException;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.regex.Pattern;

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
    private List<String> classNameList = new ArrayList<String>();
    /**
     * Spring IOC容器，管理spring bean实例
     */
    private HashMap<String, Object> ioc = new HashMap<String, Object>();
    /**
     * 保存每个requestMapping对应controller、method和parameters签名序列映射关系的集合
     */
    private List<Handler> handlerMapping = new ArrayList<Handler>();
    /**
     * freemarker模板配置
     */
    private Configuration templateConfig;

    @Override
    public void init(ServletConfig config) {
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
            doCreateBean();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        // 4、自动注入
        try {
            doInject();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        // 5、初始化handlerMapping，并交由spring管理
        doHandlerMapping();
        // 加载模板引擎
        loadTemplateConfig(config);
    }

    private void loadTemplateConfig(ServletConfig servletConfig) {
        if (null == templateConfig) {
            templateConfig = new Configuration(Configuration.VERSION_2_3_23);
            templateConfig.setDefaultEncoding("UTF-8");
            templateConfig.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            try {
                templateConfig.setDirectoryForTemplateLoading(
                        new File(servletConfig.getServletContext().getRealPath("/")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // 6、执行doDispatcher
        try {
            doDispatch(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().write("500 Server Exception\n" + e.toString());
        }
    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws IOException, InvocationTargetException, IllegalAccessException {
        String uri = req.getRequestURI().replace(req.getContextPath(), "").replaceAll("/+", "/");
        String faviconIco = "/favicon.ico";
        if (faviconIco.equals(uri)) {
            return;
        }
        Handler handler = getHandler(uri);
        if (handler == null) {
            resp.getWriter().write("404 Not Found");
            return;
        }
        Object[] args = new Object[handler.parameterNames.length];
        Map<String, String[]> reqParamMap = req.getParameterMap();
        for (int i = 0; i < handler.parameterNames.length; i++) {
            MethodArgsEntity argsEntity = handler.parameterNames[i];
            if (HttpServletRequest.class.getName().equals(argsEntity.name)) {
                args[argsEntity.index] = req;
                continue;
            }
            if (HttpServletResponse.class.getName().equals(argsEntity.name)) {
                args[argsEntity.index] = resp;
                continue;
            }
            String[] temp = reqParamMap.get(argsEntity.name);
            if (argsEntity.required && null == temp) {
                resp.getWriter().write(String.format("500 Not found parameter \"%s\"", argsEntity.name));
                return;
            } else {
                // 如果请求的参数列表中存在同名参数，读取最后一个同名参数的值
                String lastTemp = null == temp || temp.length == 0 ? null : temp[temp.length - 1];
                Object value = lastTemp;
                // 参数类型解析
                if (null != value) {
                    Class<?> clazz = handler.parameterNames[i].type;
                    if (clazz == Integer.class || clazz == int.class) {
                        value = Integer.parseInt(lastTemp);
                    } else if (clazz == Long.class || clazz == long.class) {
                        value = Long.parseLong(lastTemp);
                    } else if (clazz == Short.class || clazz == short.class) {
                        value = Short.parseShort(lastTemp);
                    } else if (clazz == Boolean.class || clazz == boolean.class) {
                        value = Boolean.parseBoolean(lastTemp);
                    }
                }
                args[argsEntity.index] = value;
            }
        }
        System.out.println("Execute : " + handler.method.toString());
        // 反射执行handler方法
        Object result = handler.method.invoke(handler.controller, args);
        // 模板引擎渲染返回HTML
        try {
            processResult(result, resp);
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }

    private void processResult(Object result, HttpServletResponse resp) throws IOException, TemplateException {
        if (result instanceof MyModelAndView) {
            MyModelAndView modelAndView = (MyModelAndView) result;
            Template template = templateConfig.getTemplate(modelAndView.getViewName());
            resp.setContentType("text/html;charset=utf-8");
            resp.setCharacterEncoding("UTF-8");
            resp.setStatus(200);
            template.process(modelAndView.getModels(), resp.getWriter());
        }
    }

    /**
     * 由请求的url获取对象的handler处理器
     * 优先识别精确匹配，如果不存在则使用模糊匹配，都不存在返回NULL
     *
     * @param uri
     * @return
     */
    private Handler getHandler(String uri) {
        Handler handler = null;
        for (Handler h : handlerMapping) {
            if (h.urlPattern.pattern().equals(uri)) {
                return h;
            }
            handler = null == handler && h.urlPattern.matcher(uri).matches() ? handler : null;
        }
        return handler;
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

    /**
     * 创建采用注解方式声明的Bean实例
     *
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private void doCreateBean() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
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
                    // 创建byName实例
                    ioc.put(lowerFirstChar(clazz.getSimpleName()), object);
                    // 创建ByType实例
                    ioc.put(clazz.getName(), object);
                } else {
                    ioc.put(value, object);
                }
                Class<?>[] interfaces = clazz.getInterfaces();
                for (Class<?> anInterface : interfaces) {
                    ioc.put(lowerFirstChar(anInterface.getSimpleName()), object);
                    if (ioc.get(anInterface.getName()) != null) {
                        System.err.println("Bean实例已经初始化，不允许重复创建实例");
                        continue;
                    }
                    ioc.put(anInterface.getName(), object);
                }
            }
        }
    }

    /**
     * 依赖注入
     */
    private void doInject() throws IllegalAccessException {
        if (ioc.isEmpty()) {
            return;
        }
        for (Map.Entry<String, Object> entry : ioc.entrySet()) {
            Object bean = entry.getValue();
            for (Field field : bean.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(MyAutowired.class)) {
                    String name = field.getType().getName();
                    field.set(bean, ioc.get(name));
                } else if (field.isAnnotationPresent(MyResource.class)) {
                    String name = field.getName();
                    field.set(bean, ioc.get(name));
                }
            }
        }
    }

    /**
     * 处理 requestMapper 和 handler 映射
     */
    private void doHandlerMapping() {
        if (ioc == null) {
            return;
        }
        for (Map.Entry<String, Object> entry : ioc.entrySet()) {
            Object controller = entry.getValue();
            if (!controller.getClass().isAnnotationPresent(MyController.class)) {
                continue;
            }
            MyRequestMapping controllerRequestMapping = controller.getClass().getAnnotation(MyRequestMapping.class);
            String controllerUrl = "";
            if (controllerRequestMapping != null) {
                controllerUrl = controllerRequestMapping.value();
            }
            Method[] methods = controller.getClass().getMethods();
            for (Method method : methods) {
                if (!method.isAnnotationPresent(MyRequestMapping.class)) {
                    continue;
                }
                MyRequestMapping methodRequestMapping = method.getAnnotation(MyRequestMapping.class);
                String methodUrl = methodRequestMapping.value();
                String regex = "/" + controllerUrl + methodUrl;
                // 对RequestMapper的value做非空校验
                if ("/".equals(regex)) {
                    System.err.println(method.getName() + " request mapping is not null");
                    continue;
                }
                regex = regex.replaceAll("/+", "/");
                Pattern pattern = Pattern.compile(regex);
                handlerMapping.add(new Handler(pattern, controller, method));
                System.out.println("Mapping : [" + regex + "] - " + method);
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
     * 保存每个requestMapping匹配的controller类、method方法以及方法参数签名序列的实体
     * 1、在servlet拦截到request请求后根据url找到对象的handler对象，
     * 2、使用handler保存的method、controller和parameters签名序列通过反射机制的invoke方法执行方法业务。
     */
    class Handler {
        private Pattern urlPattern;
        private Object controller;
        private Method method;
        private MethodArgsEntity[] parameterNames;

        public Handler(Pattern urlPattern, Object controller, Method method) {
            this.urlPattern = urlPattern;
            this.controller = controller;
            this.method = method;
            putParamIndexMapping(method);
        }

        private void putParamIndexMapping(Method method) {
            // 提取方法参数列表
            this.parameterNames = new MethodArgsEntity[method.getParameterCount()];
            /*
             * 一般情况下此处Parameter类实例的getName方法获取是获取不到类成员方法的参数列表名称的。
             * 因为在jdk1.8之前，编译后的.class文件不保存方法参数的实际名称，而是使用arg0、arg1、arg2表示，
             * 在jdk1.8中默认也是不开启在.class文件中写入实际名称的功能，需要在编译.java文件时加上-parameters参数，
             * 如：javac -parameters *.java。
             * 解决方法：下面使用javassist字节码技术获取类方法参数列表的实际名称，兼容jdk1.8及之前的版本
             */

            Parameter[] parameters = method.getParameters();
            String[] javassistParamNames = new String[0];
            try {
                javassistParamNames = JavassistUtils.getMethodParamNames(method);
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < parameters.length; i++) {
                // 提取方法參數中的HttpServletRequest和HttpServletResponse
                if (parameters[i].getType() == HttpServletRequest.class
                        || parameters[i].getType() == HttpServletResponse.class) {
                    this.parameterNames[i] =
                            new MethodArgsEntity(parameters[i].getType().getName(), i, parameters[i].getType(), true);
                    continue;
                }

                this.parameterNames[i] = new MethodArgsEntity(javassistParamNames[i], i, parameters[i].getType(), true);
                // 如果方法参数声明了@MyRequestParam注解，优先使用注解定义的value作为参数名称
                if (parameters[i].isAnnotationPresent(MyRequestParam.class)) {
                    MyRequestParam requestParam = parameters[i].getAnnotation(MyRequestParam.class);
                    if (!"".equals(requestParam.value())) {
                        this.parameterNames[i] = new MethodArgsEntity(requestParam.value(), i, parameters[i].getType(), requestParam.required());
                    } else {
                        this.parameterNames[i] = new MethodArgsEntity(javassistParamNames[i], i, parameters[i].getType(), requestParam.required());
                    }
                }
            }
        }
    }

    class MethodArgsEntity {
        private String name;
        private int index;
        private Class<?> type;
        private boolean required;

        public MethodArgsEntity(String name, int index, Class<?> type, boolean require) {
            this.name = name;
            this.index = index;
            this.type = type;
            this.required = require;
        }
    }
}
