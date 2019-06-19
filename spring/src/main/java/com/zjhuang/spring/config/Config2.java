package com.zjhuang.spring.config;

import com.zjhuang.spring.config.custom.RoleFactoryBean;
import org.springframework.context.annotation.Bean;

/**
 * 配置类
 *
 * @author zjhuang
 * @create 2019/6/6 16:38
 **/
//@Configuration
public class Config2 {

    @Bean
    public RoleFactoryBean roleFacotryBean() {
        return new RoleFactoryBean();
    }

}
