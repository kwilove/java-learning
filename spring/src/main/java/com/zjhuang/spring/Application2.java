package com.zjhuang.spring;

import com.zjhuang.spring.config.Config2;
import com.zjhuang.spring.config.custom.RoleFactoryBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 启动类
 *
 * @author zjhuang
 * @create 2019/6/6 16:21
 **/
public class Application2 {

    public static void main(String[] args) {
        // 加载配置类，读取配置类中定义的信息
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config2.class);
        // operation 1
        System.out.println(context.getBean("roleFacotryBean"));
        // operation 2
        System.out.println(context.getBean("roleFacotryBean"));
        // operation 3
        System.out.println(context.getBean("&roleFacotryBean"));
        // operation 4
        System.out.println(context.getBean(RoleFactoryBean.class));
        context.close();
    }

}
