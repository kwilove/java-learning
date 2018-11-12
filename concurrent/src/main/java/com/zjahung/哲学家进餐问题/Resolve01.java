package com.zjahung.哲学家进餐问题;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 解决哲学家进餐问题的第一种方法
 * <p>
 * 允许最多只能有四位科学家拿起左边的筷子，这种方案可以保证在最坏的情况下都至少有一位哲学家可以进餐成功，
 * 然后释放筷子，防止死锁的发生。
 *
 * @author zjhuang
 * @create 2018/10/16 13:24
 **/
public class Resolve01 {
    /**
     * 哲学家人数和筷子数量
     */
    static final int num = 5;
    /**
     * 创建大小为5的信号量数组，模拟5根筷子
     */
    static final Semaphore[] semaphores = new Semaphore[num];
    /**
     * 定义一个大小为4的限制请求左边筷子的信号量，每位哲学家在请求左边筷子前都需要先请求这个信号量的许可
     */
    static final Semaphore leftSemaphore = new Semaphore(num - 1, true);

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
                    // 请求获取左边筷子的许可
                    leftSemaphore.acquire();
                    // 请求左边的筷子
                    semaphores[i].acquire();
                    // 请求右边的筷子
                    semaphores[(i + 1) % num].acquire();
                    // 进餐
                    System.out.println("我是" + (i + 1) + "号科学家，我正在进餐...");
                    // 释放左边的筷子
                    semaphores[i].release();
                    // 释放请求左边筷子的许可
                    leftSemaphore.release();
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
