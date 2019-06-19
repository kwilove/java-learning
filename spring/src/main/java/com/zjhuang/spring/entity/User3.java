package com.zjhuang.spring.entity;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class User3 {

    @PostConstruct
    public void init() {
        System.out.println("user3 init");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("user3 destroy");
    }

    public void print() {
        System.out.println("This is a user3 class.");
    }
}
