package com.zjahung.threadpool;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 自定义实现的线程池
 *
 * @author zjhuang
 * @create 2018/9/20 10:55
 **/
public class MyThreadPoolExecutor extends MyAbstractExecutorService {
    /**
     * 核心线程数
     */
    private volatile int corePoolSize;
    /**
     * 最大线程数
     */
    private volatile int maximumPoolSize;
    /**
     * 多余空闲线程保持存活的时间
     */
    private volatile long keepAliveTime;
    /**
     * 存活时间单位
     */
    private volatile TimeUnit unit;
    /**
     * 任务等待队列
     */
    private final BlockingQueue<Runnable> workQueue;
    /**
     * 任务拒绝策略
     */
    private volatile RejectedExecutionHandler handler;
    /**
     * 是否允许核心线程在超出存活时间后关闭
     */
    private volatile boolean allowCoreThreadTimeOut;
    /**
     * 当前线程池中线程数
     */
    private AtomicInteger activeThreadCount = new AtomicInteger(0);
    private ReentrantLock mainLock = new ReentrantLock();

    public MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit
            , BlockingQueue<Runnable> workQueue) {
        this(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, new ThreadPoolExecutor.DiscardPolicy());
    }

    public MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit
            , BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        this.corePoolSize = corePoolSize;
        this.maximumPoolSize = maximumPoolSize;
        this.keepAliveTime = keepAliveTime;
        this.unit = unit;
        this.workQueue = workQueue;
        this.handler = handler;
        this.allowCoreThreadTimeOut = false;
    }

    @Override
    public void execute(Runnable runnable) {
        if (runnable == null) {
            throw new NullPointerException();
        }
        int c = activeThreadCount.get();
        if (c < corePoolSize) {
            if (addWorker(runnable, true)) {
                return;
            }
        }
        if (workQueue.offer(runnable)) {
        } else if (!addWorker(runnable, false)) {
            reject(runnable);
        }
    }

    private void reject(Runnable runnable) {
        handler.rejectedExecution(runnable, null);
    }

    private boolean addWorker(Runnable firstTask, boolean core) {
        if (firstTask == null && workQueue.isEmpty()) {
            return false;
        }
        if (activeThreadCount.get() >= (core ? corePoolSize : maximumPoolSize)) {
            return false;
        }
        Worker w = new Worker(firstTask);
        final Thread t = w.thread;
        if (t != null) {
            activeThreadCount.incrementAndGet();
            System.out.println("+" + activeThreadCount.get());
            t.start();
        }
        return true;
    }

    class Worker extends ReentrantLock implements Runnable {
        final Thread thread;
        Runnable firstTask;

        Worker(Runnable firstTask) {
            this.firstTask = firstTask;
            this.thread = new Thread(this);
        }

        @Override
        public void run() {
            runWorker(this);
        }

        final void runWorker(Worker w) {
            Runnable task = w.firstTask;
            w.firstTask = null;
            try {
                while (task != null || (task = getTask()) != null) {
                    w.lock();
                    try {
                        task.run();
                    } finally {
                        task = null;
                        w.unlock();
                    }
                }
            } finally {
                processWorkerExit(w);
            }
        }

        private void processWorkerExit(Worker w) {
            if (!addWorker(null, false)) {
            }
        }

        private Runnable getTask() {
            for (; ; ) {
                int threadCount = activeThreadCount.get();
                boolean timed = allowCoreThreadTimeOut || threadCount > corePoolSize;
                if (timed && workQueue.isEmpty()) {
                    activeThreadCount.decrementAndGet();
                    System.out.println("-" + activeThreadCount.get());
                    return null;
                }
                try {
                    Runnable r = timed ?
                            workQueue.poll(keepAliveTime, unit) :
                            workQueue.take();
                    if (r != null) {
                        return r;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取线程池中的活跃线程数量
     *
     * @return
     */
    public int getActiveThreadCount() {
        return activeThreadCount.get();
    }

    /**
     * 控制核心线程是否启用存活时间机制
     *
     * @param value
     */
    public void allowCoreThreadTimeOut(boolean value) {
        if (value && keepAliveTime <= 0) {
            throw new IllegalArgumentException("核心线程存活时间必须大于0");
        }
        if (value != allowCoreThreadTimeOut) {
            allowCoreThreadTimeOut = value;
            if (value) {
                // TODO 实现停止空闲的核心线程
            }
        }
    }

}
