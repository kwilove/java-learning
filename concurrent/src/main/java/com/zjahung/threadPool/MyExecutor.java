package com.zjahung.threadPool;

/**
 * 自定义执行器接口
 *
 * @author zjhuang
 * @create 2018/9/20 10:55
 **/
public interface MyExecutor {
    void execute(Runnable runnable);
}
