package com.zjhuang.utils;

import java.util.Arrays;
import java.util.Random;

/**
 * 整型数组生成工具
 *
 * @author zjhuang
 * @create 2018/9/23 14:50
 **/
public class IntArrayGenerater {

    /**
     * 生成随机数數組
     * @param len   数组长度
     * @param max   元素最大值
     * @return
     */
    public static int[] generate(int len, int max) {
        return generate(len, 0, max);
    }

    /**
     * 生成随机数數組
     * @param len   数组长度
     * @param min   元素最小值
     * @param max   元素最大值
     * @return
     */
    public static int[] generate(int len, int min, int max) {
        int[] array;
        if (len == 0) {
            return new int[0];
        }
        array = new int[len];
        Random random = new Random();
        for (int i = 0; i < len; i++) {
            int n = random.nextInt(max + 1);
            array[i] = n < min ? n + min : n;
        }
        return array;
    }

}
