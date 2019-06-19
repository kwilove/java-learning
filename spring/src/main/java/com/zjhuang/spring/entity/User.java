package com.zjhuang.spring.entity;

/**
 * 用户信息实体
 *
 * @author zjhuang
 * @create 2019/6/10 17:39
 **/
public class User {

    private String name;
    private int age;
    private boolean sex;

    public void init() {
        System.out.println("user init");
    }

    public void destroy() {
        System.out.println("user destroy");
    }

    public void print() {
        System.out.println("This is a user bean base on configuration class.");
    }
}
