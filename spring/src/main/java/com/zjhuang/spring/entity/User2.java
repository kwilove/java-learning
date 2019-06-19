package com.zjhuang.spring.entity;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author zjhuang
 * @create 2019/6/13 20:56
 **/
public class User2 implements InitializingBean, DisposableBean {

    public void destroy() throws Exception {
        System.out.println("user2 destroy");
    }

    public void afterPropertiesSet() throws Exception {
        System.out.println("user2 afterPropertiesSet");
    }

    public void print() {
        System.out.println("This is a user2 class");
    }
}
