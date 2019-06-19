package com.zjhuang.springboot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author zjhuang
 * @create 2019/6/6 17:35
 **/
@Component
@PropertySource(value = "classpath:test.yml")
public class YamlProperties1 {

    @Value("${system.user.name}")
    private String name;
    @Value("${system.user.password}")
    private String password;
    @Value("${system.user.age}")
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "YamlProperties1{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                '}';
    }
}
