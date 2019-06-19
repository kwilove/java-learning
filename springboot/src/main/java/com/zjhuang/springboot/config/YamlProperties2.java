package com.zjhuang.springboot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author zjhuang
 * @create 2019/6/6 17:18
 **/
@Component
@PropertySource(value = "classpath:test.yml")
@ConfigurationProperties(prefix = "system.user")
public class YamlProperties2 {

    @Value("${name}")
    private String name;
    @Value("${password}")
    private String password;
    @Value("${age}")
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
        return "YamlProperties2{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                '}';
    }
}
