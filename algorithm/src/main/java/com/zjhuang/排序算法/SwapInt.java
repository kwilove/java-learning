package com.zjhuang.排序算法;

/**
 * 整型数据交换的三种方式
 *
 * @author zjhuang
 * @create 2018/9/23 19:24
 **/
public class SwapInt {
    /**
     * 通过一个临时变量实现交换
     * @param a
     * @param b
     * @return
     */
    public static int[] swapByTemp(int a, int b) {
        int temp = a;
        a = b;
        b = temp;
        return new int[] {a, b};
    }

    /**
     * 使用两个整数之和与整数做减法的方式实现交换
     * @param a
     * @param b
     * @return
     */
    public static int[] swapBySum(int a, int b) {
        a = a + b;
        b = a - b;
        a = a - b;
        return new int[] {a, b};
    }

    /**
     * 使用异或运算交换两个整数
     * @param a
     * @param b
     * @return
     */
    public static int[] swapByXor(int a, int b) {
        a = a ^ b;
        b = a ^ b;
        a = a ^ b;
        return new int[] {a, b};
    }

}
