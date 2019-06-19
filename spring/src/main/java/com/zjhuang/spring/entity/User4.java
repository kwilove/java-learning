package com.zjhuang.spring.entity;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class User4 implements BeanPostProcessor {

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("user4: post process before " + beanName + " init");
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("user4: post process after " + beanName + " init");
        return bean;
    }

    public void print() {
        System.out.println("This is a user4 class.");
    }
}
