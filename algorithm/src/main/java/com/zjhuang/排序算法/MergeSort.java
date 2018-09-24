package com.zjhuang.排序算法;

import com.zjhuang.utils.IntArrayGenerater;

import java.util.Arrays;

/**
 * 归并排序算法
 *
 * @author zjhuang
 * @create 2018/9/23 14:47
 **/
public class MergeSort {

    public static void main(String[] args) {
        int[] array = IntArrayGenerater.generate(10, 0, 10);
        System.out.println(Arrays.toString(array));
        System.out.println(Arrays.toString(sorted(array)));
    }

    public static int[] sorted(int[] srcArray) {
        if (srcArray == null || srcArray.length == 0) {
            return null;
        }
        int mid = srcArray.length / 2;
        return sortedSubArray(srcArray, 0, srcArray.length);
    }

    private static int[] sortedSubArray(int[] array, int startIndex, int endIndex) {
        for (int i = startIndex; i < endIndex - 1; i++) {
            for (int j = i + 1; j < endIndex; j++) {
                if (array[i] > array[j]) {
                    int[] c = SwapInt.swapByXor(array[i], array[j]);
                    array[i] = c[0];
                    array[j] = c[1];
                }
            }
        }
        return array;
    }

}
