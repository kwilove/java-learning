package com.zjhuang.设计模式.生产者消费者模式;

import java.io.IOException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * 生产者、消费者模式
 * 通过Semaphore方法实现
 *
 * @author zjhuang
 * @create 2018/9/28 20:08
 **/
public class ProviderConsumer03 {

    private static Semaphore semaphore = new Semaphore(1);
    public static volatile int count = 0;

    public static void main(String[] args) throws IOException, InterruptedException {
        ThreadFactory factory = new MyThreadFactory();
//        new Thread(new Consumer()).start();
        factory.newThread(new Provider()).start();
        TimeUnit.SECONDS.sleep(1);
//        new Thread(new Provider()).start();
        factory.newThread(new Consumer()).start();
        System.in.read();
    }

    static class Provider implements Runnable {
        @Override
        public void run() {
            while (!Thread.interrupted()) {
                try {
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("provider: count = " + (++count));
                semaphore.release();
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Consumer implements Runnable {
        @Override
        public void run() {
            while (!Thread.interrupted()) {
                if (count <= 0) {
                    continue;
                }
                try {
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("consumer: count = " + (--count));
                semaphore.release();
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class MyThreadFactory implements ThreadFactory {
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r);
        }
    }
}
