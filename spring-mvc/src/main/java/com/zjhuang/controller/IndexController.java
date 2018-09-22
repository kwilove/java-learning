package com.zjhuang.controller;

import com.zjhuang.annotation.MyAutowired;
import com.zjhuang.annotation.MyController;
import com.zjhuang.service.IIndexService;

/**
 * @author zjhuang
 * @create 2018/9/21 14:53
 **/
@MyController
public class IndexController {

    @MyAutowired
    private IIndexService indexService;

}
