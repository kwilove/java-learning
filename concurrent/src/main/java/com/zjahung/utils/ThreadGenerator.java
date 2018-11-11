package com.zjahung.utils;

import java.util.concurrent.CountDownLatch;

/**
 * 线程工具
 *
 * @author zjhuang
 * @create 2018/9/20 14:40
 **/
public class ThreadGenerator {

    public static void createAndStart(int nThreads, Runnable task) {
        final CountDownLatch countDownLatch1 = new CountDownLatch(1);
        final CountDownLatch countDownLatch2 = new CountDownLatch(nThreads);
        for (int i = 0; i < nThreads; i++) {
            new Thread(() -> {
                try {
                    countDownLatch1.await();
                    task.run();
                    countDownLatch2.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        countDownLatch1.countDown();
        try {
            countDownLatch2.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
