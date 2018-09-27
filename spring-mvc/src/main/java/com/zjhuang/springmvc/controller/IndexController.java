package com.zjhuang.springmvc.controller;

import com.zjhuang.springmvc.annotation.MyAutowired;
import com.zjhuang.springmvc.annotation.MyController;
import com.zjhuang.springmvc.annotation.MyRequestMapping;
import com.zjhuang.springmvc.annotation.MyRequestParam;
import com.zjhuang.modelandview.MyModelAndView;
import com.zjhuang.springmvc.service.IIndexService;

/**
 * @author zjhuang
 * @create 2018/9/21 14:53
 **/
@MyController
public class IndexController {

    @MyAutowired
    private IIndexService indexService;

    @MyRequestMapping(value = "/")
    public MyModelAndView index(@MyRequestParam(value = "name") String name) {
        MyModelAndView mv = new MyModelAndView("/index.html");
        indexService.printStr(name);
        mv.getModels().put("name", name);
        return mv;
    }

}
