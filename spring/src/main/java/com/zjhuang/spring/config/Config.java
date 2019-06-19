package com.zjhuang.spring.config;

import com.zjhuang.spring.config.custom.FirstCondition;
import com.zjhuang.spring.config.custom.RoleFactoryBean;
import com.zjhuang.spring.entity.User;
import org.springframework.context.annotation.*;

/**
 * 配置类
 *
 * @author zjhuang
 * @create 2019/6/6 16:38
 **/
//@Configuration
//@ComponentScan(
//        basePackages = "com.zjhuang.spring",
//        includeFilters = {
//                @ComponentScan.Filter(type = FilterType.ANNOTATION, value = {Service.class, Repository.class})
//        },
//        useDefaultFilters = false
//)
//@ComponentScan(basePackages = "com.zjhuang.spring")
//@Import(value = Role.class)
//@Import(value = FirstImportSelector.class)
//@Import(value = FirstImportBeanDefinitionRegister.class)
public class Config {

    @Bean(name = "user", initMethod = "init", destroyMethod = "destroy")
    public User user() {
        return new User();
    }

    @Bean(name = "user_dev")
    @Profile(value = "dev")
    public User devUser() {
        return new User();
    }

    @Bean(name = "user_prod")
    @Profile(value = "prod")
    public User prodUser() {
        return new User();
    }

    @Bean(name = "prototypeUser")
    @Scope(value = "prototype")
    public User prototypeUser() {
        return new User();
    }

    @Bean(name = "lazyUser")
    @Lazy
    public User lazyUser() {
        return new User();
    }

    @Bean(name = "conditionalUser")
    @Conditional(value = FirstCondition.class)
    public User conditionalUser() {
        return new User();
    }

    @Bean
    public RoleFactoryBean roleFacotryBean() {
        return new RoleFactoryBean();
    }

}
