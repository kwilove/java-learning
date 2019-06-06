package com.zjhuang.springmvc.service;

/**
 * @author zjhuang
 * @create 2018/9/21 14:53
 **/
public interface IIndexService {
    /**
     * 打印字符串
     *
     * @param s
     */
    public void printStr(String s);

    /**
     * 字符串加上前缀
     *
     * @param s
     * @param prefix
     * @return
     */
    public String prependPrefix(String s, String prefix);

}
