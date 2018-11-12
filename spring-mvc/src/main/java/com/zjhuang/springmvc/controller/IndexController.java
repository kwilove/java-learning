package com.zjhuang.springmvc.controller;

import com.zjhuang.modelandview.MyModelAndView;
import com.zjhuang.springmvc.annotation.MyAutowired;
import com.zjhuang.springmvc.annotation.MyController;
import com.zjhuang.springmvc.annotation.MyRequestMapping;
import com.zjhuang.springmvc.annotation.MyRequestParam;
import com.zjhuang.springmvc.service.IIndexService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author zjhuang
 * @create 2018/9/21 14:53
 **/
@MyController
public class IndexController {

    @MyAutowired
    private IIndexService indexService;

    @MyRequestMapping(value = "/")
    public MyModelAndView index(HttpServletRequest request, HttpServletResponse response,
                                @MyRequestParam(required = false) String name, Date age) {
        MyModelAndView mv = new MyModelAndView("/index.html");
        indexService.printStr(name);
        mv.getModels().put("name", name);
        mv.getModels().put("age", age);
        return mv;
    }

    public void test() {
        System.out.println("Test");
    }

}
