package com.zjhuang.设计模式.生产者消费者模式;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生产者、消费者模式
 * 通过ReentrantLock + Condition方法实现
 *
 * @author zjhuang
 * @create 2018/9/28 20:08
 **/
public class ProviderConsumer02 {

    private static ReentrantLock lock = new ReentrantLock();
    private static Condition provider = lock.newCondition();
    private static Condition consumer = lock.newCondition();
    public static volatile int count = 0;

    public static void main(String[] args) throws IOException, InterruptedException {
        new Thread(new Consumer()).start();
        TimeUnit.SECONDS.sleep(1);
        new Thread(new Provider()).start();
        System.in.read();
    }

    static class Provider implements Runnable {
        @Override
        public void run() {
            lock.lock();
            try {
                while (!Thread.interrupted()) {
                    if (count > 5) {
                        try {
                            consumer.signal();
                            provider.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("provider: count = " + (++count));
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } finally {
                if (lock.isLocked()) {
                    lock.unlock();
                }
            }
        }
    }

    static class Consumer implements Runnable {
        @Override
        public void run() {
            lock.lock();
            try {

                while (!Thread.interrupted()) {
                    if (count <= 0) {
                        try {
                            consumer.await();
                            provider.signal();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("consumer: count = " + (--count));
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } finally {
                if (lock.isLocked()) {
                    lock.unlock();
                }
            }
        }
    }
}
