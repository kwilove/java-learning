package com.zjhuang.spring;

import com.zjhuang.spring.config.Config;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 启动类
 *
 * @author zjhuang
 * @create 2019/6/6 16:21
 **/
public class Application {

    public static void main(String[] args) {
        // 加载xml配置文件
        // ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        // 加载配置类，读取配置类中定义的信息
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.getEnvironment().setActiveProfiles("prod");
        context.register(Config.class);
        context.refresh();

        // 遍历打印容器中的Bean实例名称
        for (String beanDefinitionName : context.getBeanDefinitionNames()) {
            System.out.println("bean name: " + beanDefinitionName);
        }
        // 从容器中获取bean实例
        // UserService userService = (UserService) context.getBean("userService");
        // 执行实例方法
        // userService.print();
        context.close();
    }

}
