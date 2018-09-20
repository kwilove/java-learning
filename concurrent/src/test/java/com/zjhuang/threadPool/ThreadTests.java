package com.zjhuang.threadPool;

import com.zjahung.threadPool.MyExecutorService;
import com.zjahung.threadPool.MyThreadPoolExecutor;
import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author zjhuang
 * @create 2018/9/20 13:58
 **/
public class ThreadTests {

    @Test
    public void test01() {
        MyExecutorService executorService = new MyThreadPoolExecutor(1, 2
                , 5, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(3));
        int threadCount = 5;
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            executorService.execute(() -> {
                System.out.println(Thread.currentThread().getName());
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(((MyThreadPoolExecutor) executorService).getActiveThreadCount());
        executorService.execute(() -> System.out.println("This is the last task"));
    }

}
