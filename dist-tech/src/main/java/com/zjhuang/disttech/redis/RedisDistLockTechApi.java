package com.zjhuang.disttech.redis;

import jdk.nashorn.internal.objects.annotations.Constructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Redis分布式锁测试接口
 *
 * @author zjhuang
 * @create 2019/10/23 17:31
 **/
@RestController
public class RedisDistLockTechApi {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private final String STOCK_KEY = "stock";

    @Constructor
    public void init() {
        // 初始化商品库存为50
        stringRedisTemplate.opsForValue().set(STOCK_KEY, String.valueOf(50));
    }

    /**
     * 商品抢购
     */
    @GetMapping("/goods/rush_to_buy")
    public String rushToBuy() {
        int stock = Integer.parseInt(stringRedisTemplate.opsForValue().get(STOCK_KEY));
        if (stock <= 0) {
            System.out.println("购买失败，库存不足");
            return "no enough";
        }
        int currStock = stock - 1;
        stringRedisTemplate.opsForValue().set(STOCK_KEY, String.valueOf(currStock));
        System.out.println("购买成功，剩余库存：" + currStock);
        return "success";
    }
}
