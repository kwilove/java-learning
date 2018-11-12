package com.zjhuang.排序算法;

import com.zjhuang.utils.IntArrayGenerater;

import java.util.Arrays;

/**
 * 归并排序算法
 *
 * @author zjhuang
 * @create 2018/9/23 14:47
 **/
public class MergeSort extends AbstractSort {
    /*
     * @算法步骤：
     * 1、将数组序列拆分成左右两个子序列，再按照同样方法对左右两个子序列进行拆分，
     * 直到变为不可再拆分的最小子序列，即元素个数为一
     * 2、
     */

    @Override
    int[] sorted(int[] array) {
        if (array == null || array.length == 0) {
            return array;
        }
        int[] temp = new int[array.length];
        split(array, temp, 0, array.length - 1);
        return array;
    }

    public void split(int[] array, int[] temp, int left, int right) {
        if (left < right) {
            int mid = (left + right) >> 1;
            split(array, temp, left, mid);
            split(array, temp, mid + 1, right);
            merge(array, temp, left, mid, right);
        }
    }

    public void merge(int[] array, int[] temp, int left, int mid, int right) {
        int i = left;
        int j = mid + 1;
        int k = i;
        while (i <= mid && j <= right) {
            if (array[i] <= array[j]) {
                temp[k++] = array[i++];
            } else {
                temp[k++] = array[j++];
            }
        }
        while (i <= mid) {
            temp[k++] = array[i++];
        }
        while (j <= right) {
            temp[k++] = array[j++];
        }
        // 将排好序的队列赋值给原数组
        for (i = left; i <= right; i++) {
            array[i] = temp[i];
        }
    }

    public static void main(String[] args) {
        int[] array = IntArrayGenerater.generate(100000, 0, 10000);
        System.out.println(Arrays.toString(array));
        System.out.println(Arrays.toString(new MergeSort().sorted(array)));
    }

}
