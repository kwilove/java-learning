package com.zjhuang.排序算法;

import com.zjhuang.utils.IntArrayGenerater;

import java.util.Arrays;

/**
 * 冒泡排序算法
 *
 * @author zjhuang
 * @create 2018/9/23 19:41
 **/
public class BubbleSort extends AbstractSort {
    /*
     * @实现原理：
     * 1、从未排序队列的首个元素开始，依次比较相邻两个元素，如果第一个元素大于第二个元素，则交换它们的位置，
     * 按照上面的方法进行一轮操作之后，最大的元素就会跑到队列的队尾。
     * 2、对未排序队列重复第一步的操作，最终会转换为一个有序序列。
     *
     * @时间复杂度：
     * @空间复杂度：
     */

    @Override
    int[] sorted(int[] array) {
        if (array == null || array.length == 0) {
            return null;
        }
        int length = array.length;
        for (int i = 0; i < length - 1; i++) {
            for (int j = 0; j < length - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    int[] c = swap(array[j], array[j + 1]);
                    array[j] = c[0];
                    array[j + 1] = c[1];
                }
            }
        }
        return array;
    }

    public static void main(String[] args) {
        int[] array = IntArrayGenerater.generate(10, 0, 10);
        System.out.println(Arrays.toString(array));
        System.out.println(Arrays.toString(new BubbleSort().sorted(array)));
    }
}
