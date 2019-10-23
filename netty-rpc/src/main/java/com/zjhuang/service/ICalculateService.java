package com.zjhuang.service;

/**
 * 计算服务接口
 *
 * @author zjhuang
 * @create 2018/11/25 14:26
 **/
public interface ICalculateService {
    /**
     * 加法运算
     *
     * @param param1
     * @param param2
     * @return
     */
    double add(double param1, double param2);

    /**
     * 减法运算
     *
     * @param param1
     * @param param2
     * @return
     */
    double sub(double param1, double param2);

    /**
     * 乘法运算
     *
     * @param param1
     * @param param2
     * @return
     */
    double mul(double param1, double param2);

    /**
     * 除法运算
     *
     * @param param1
     * @param param2
     * @return
     */
    double div(double param1, double param2);
}
