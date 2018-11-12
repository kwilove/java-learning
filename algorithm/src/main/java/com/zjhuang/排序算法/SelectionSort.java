package com.zjhuang.排序算法;

/**
 * 选择排序算法
 *
 * @author zjhuang
 * @create 2018/9/23 19:48
 **/
public class SelectionSort extends AbstractSort {
    /*
     * @算法步骤：
     * 1、首先从未排序序列中寻找最小元素，放入到排序序列的起始位置，
     * 2、然后接着从剩下的未排序序列中寻找最小元素放入到已排序序列末尾位置，
     * 3、直到所有元素都加入到排序序列中为止。
     *
     * @时间复杂度：O(n^2)
     * @空间复杂度：O(1)
     */

    @Override
    public int[] sorted(int[] array) {
        if (array == null || array.length == 0) {
            return null;
        }
        int length = array.length;
        for (int i = 0; i < length - 1; i++) {
            for (int j = i + 1; j < length; j++) {
                if (array[i] > array[j]) {
                    int[] c = swap(array[i], array[j]);
                    array[i] = c[0];
                    array[j] = c[1];
                }
            }
        }
        return array;
    }

}
