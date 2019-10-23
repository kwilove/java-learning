package com.zjhuang;

import com.zjhuang.netty.proxy.CalculateServiceProxy;

/**
 * 客户端启动类
 *
 * @author zjhuang
 * @create 2018/11/26 18:58
 **/
public class Client {

    public static void main(String[] args) throws Exception {
        String host = "127.0.0.1";
        int port = 8080;
        CalculateServiceProxy proxy = new CalculateServiceProxy(host, port);
        System.out.println(proxy.add(1.0, 2.0));
        System.out.println(proxy.sub(1.0, 2.0));
        System.out.println(proxy.mul(1.0, 2.0));
        System.out.println(proxy.div(1.0, 2.0));
    }
}
