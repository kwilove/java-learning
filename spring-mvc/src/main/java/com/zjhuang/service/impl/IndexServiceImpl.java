package com.zjhuang.service.impl;

import com.zjhuang.annotation.MyService;
import com.zjhuang.service.IIndexService;

/**
 * @author zjhuang
 * @create 2018/9/21 14:54
 **/
@MyService
public class IndexServiceImpl implements IIndexService {
    @Override
    public void printStr(String s) {
        System.out.println(s);
    }

    @Override
    public String prependPrefix(String s, String prefix) {
        return prefix + s;
    }
}
