package com.zjhuang.设计模式.生产者消费者模式;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 生产者-消费者模式
 * 通过wait + notify方法实现
 *
 * @author zjhuang
 * @create 2018/9/28 19:21
 **/
public class ProviderConsumer01 {
    public static void main(String[] args) throws IOException {
        WoreHouse woreHouse = new WoreHouse();
        new Thread(new Provider(woreHouse)).start();
        new Thread(new Consumer(woreHouse)).start();
        System.in.read();
    }

    /**
     * 生产者线程
     */
    static class Provider implements Runnable {
        private WoreHouse woreHouse;

        public Provider(WoreHouse woreHouse) {
            this.woreHouse = woreHouse;
        }

        @Override
        public void run() {
            try {
                woreHouse.provide();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 消费者线程
     */
    static class Consumer implements Runnable {
        private WoreHouse woreHouse;

        public Consumer(WoreHouse woreHouse) {
            this.woreHouse = woreHouse;
        }

        @Override
        public void run() {
            try {
                woreHouse.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 仓库对象，提供生产、消息业务方法
     */
    static class WoreHouse {
        public int count = 0;
        public int max = 5;
        public int min = 0;

        public synchronized void provide() throws InterruptedException {
            for (; ; ) {
                while (count >= max) {
                    wait();
                }
                System.out.println("provider: count = " + (++count));
                notifyAll();
                TimeUnit.SECONDS.sleep(1);
            }
        }

        public synchronized void consume() throws InterruptedException {
            for (; ; ) {
                while (count <= min) {
                    wait();
                }
                System.out.println("consumer: count = " + (--count));
                notifyAll();
                TimeUnit.SECONDS.sleep(1);
            }
        }
    }
}

