package com.zjahung.哲学家进餐问题;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 实现哲学家进餐问题代码
 *
 * @author zjhuang
 * @create 2018/10/16 13:24
 **/
public class Question {
    /**
     * 哲学家人数和筷子数量
     */
    static final int num = 5;
    /**
     * 创建大小为5的信号量数组，模拟5根筷子
     */
    static final Semaphore[] semaphores = new Semaphore[num];

    public static void main(String[] args) {
        // 初始化信号量
        for (int i = 0; i < semaphores.length; i++) {
            semaphores[i] = new Semaphore(1, true);
        }

        // 创建大小为5的线程池
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        // 创建5个哲学家，这里会产生死锁问题
        for (int i = 0; i < num; i++) {
            executorService.execute(new Eat(i));
        }
    }

    /**
     * 哲学家进餐、思考线程
     */
    static class Eat extends Thread {
        private int i = 0;

        public Eat(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            while (!Thread.interrupted()) {
                try {
                    // 请求左边的筷子
                    semaphores[i].acquire();
                    // 请求右边的筷子
                    semaphores[(i + 1) % num].acquire();
                    // 进餐
                    System.out.println("我是" + (i + 1) + "号科学家，我正在进餐...");
                    // 释放左边的筷子
                    semaphores[i].release();
                    // 释放右边的筷子
                    semaphores[(i + 1) % num].release();
                    // 思考
                    System.out.println("我是" + (i + 1) + "号科学家，我吃饱了，开始思考...");
                    // 释放CPU调度权，让其他哲学家线程执行
                    Thread.yield();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
