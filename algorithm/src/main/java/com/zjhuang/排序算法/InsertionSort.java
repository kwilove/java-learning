package com.zjhuang.排序算法;

import com.zjhuang.utils.IntArrayGenerater;

import java.util.Arrays;

/**
 * 插入排序算法
 *
 * @author zjhuang
 * @create 2018/9/28 18:06
 **/
public class InsertionSort extends AbstractSort {
    /*
     * @算法步骤
     * 1、将第一待排序序列第一个元素看做一个有序序列，把第二个元素到最后一个元素当成是未排序序列。
     * 2、从头到尾依次扫描未排序序列，将扫描到的每个元素插入有序序列的适当位置，
     * 如果待插入的元素与有序序列中的某个元素相等，则将待插入元素插入到相等元素的后面。
     */

    @Override
    int[] sorted(int[] array) {
        int length = array.length;
        for (int i = 1; i < length; i++) {
            int value = array[i];
            for (int j = i - 1; j >= 0; j--) {
                if (value >= array[j]) {
                    break;
                }
                array[j + 1] = array[j];
                array[j] = value;
            }
        }
        return array;
    }

    public static void main(String[] args) {
        int[] array = IntArrayGenerater.generate(10, 0, 100);
        System.out.println(Arrays.toString(array));
        System.out.println(Arrays.toString(new InsertionSort().sorted(array)));
    }
}
