package com.zjhuang;

import com.zjhuang.netty.registry.RpcServiceRegistry;
import com.zjhuang.netty.server.RpcServer;
import com.zjhuang.service.ICalculateService;
import com.zjhuang.service.impl.CalculateServiceImpl;

/**
 * 服务器启动类
 *
 * @author zjhuang
 * @create 2018/11/26 18:57
 **/
public class Server {

    public static void main(String[] args) throws Exception {
        int port = 8080;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }

        RpcServiceRegistry rpcServiceRegistry = new RpcServiceRegistry();
        rpcServiceRegistry.registerService(ICalculateService.class, new CalculateServiceImpl());
        new RpcServer(port).start(rpcServiceRegistry);
    }
}
