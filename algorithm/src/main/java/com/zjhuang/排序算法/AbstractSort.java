package com.zjhuang.排序算法;

/**
 * 排序接口
 * Created by zjhuang on 2018/9/23 19:49.
 */
public abstract class Sort {
    /**
     * 排序
     * @param array 排序前的數組
     * @return
     */
    abstract int[] sorted(int[] array);

    /**
     * 整数交换
     * @param a
     * @param b
     * @return
     */
    int[] swap(int a, int b) {
        return SwapInt.swapByXor(a, b);
    }
}
