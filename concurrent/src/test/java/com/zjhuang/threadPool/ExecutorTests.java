package com.zjhuang.threadPool;

import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池测试类
 *
 * @author zjhuang
 * @create 2018/9/22 16:51
 **/
public class ExecutorTests {

    @Test
    public void test01() throws InterruptedException {
        ThreadPoolExecutor executorService = new ThreadPoolExecutor(1,3
                ,10, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(5),
                new ThreadPoolExecutor.DiscardPolicy());
        for (int i = 0; i < 7; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName());
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        System.out.println(executorService.getQueue().size());
        Thread.sleep(5000);
    }
}
