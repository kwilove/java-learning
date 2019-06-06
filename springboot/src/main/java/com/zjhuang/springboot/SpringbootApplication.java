package com.zjhuang.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 程序启动类
 *
 * @author zjhaung
 */
@SpringBootApplication
@RestController
@ServletComponentScan
public class SpringbootApplication {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
    }

    @RequestMapping("/")
    public String index() {
        log.info("This is index page");
        return "Welcome!";
    }
}
