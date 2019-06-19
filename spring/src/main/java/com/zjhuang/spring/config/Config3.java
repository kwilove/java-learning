package com.zjhuang.spring.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * 配置类
 *
 * @author zjhuang
 * @create 2019/6/6 16:38
 **/
@Configuration
@ComponentScan(value = "com.zjhuang.spring", excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, value = {Controller.class, Service.class, Repository.class}))
public class Config3 {

//    @Bean(initMethod = "init", destroyMethod = "destroy")
//    public User user() {
//        return new User();
//    }

//    @Bean
//    public User2 user2() {
//        return new User2();
//    }

//    @Bean
//    public User3 user3() {
//        return new User3();
//    }

//    @Bean
//    public User4 user4() {
//        return new User4();
//    }
}
