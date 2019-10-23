package com.zjhuang.service.impl;

import com.zjhuang.service.ICalculateService;

/**
 * 计算服务接口实现
 *
 * @author zjhuang
 * @create 2018/11/25 14:27
 **/
public class CalculateServiceImpl implements ICalculateService {

    @Override
    public double add(double param1, double param2) {
        return param1 + param2;
    }

    @Override
    public double sub(double param1, double param2) {
        return param1 - param2;
    }

    @Override
    public double mul(double param1, double param2) {
        return param1 * param2;
    }

    @Override
    public double div(double param1, double param2) {
        return param1 / param2;
    }
}
