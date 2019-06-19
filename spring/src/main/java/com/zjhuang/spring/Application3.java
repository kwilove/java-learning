package com.zjhuang.spring;

import com.zjhuang.spring.config.Config3;
import com.zjhuang.spring.entity.User4;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 启动类
 *
 * @author zjhuang
 * @create 2019/6/6 16:21
 **/
public class Application3 {

    public static void main(String[] args) {
        // 加载配置类，读取配置类中定义的信息，创建容器
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config3.class);
        // 获取容器Bean对象，并执行对象方法
        context.getBean(User4.class).print();
        // 关闭容器
        context.close();
    }
}
