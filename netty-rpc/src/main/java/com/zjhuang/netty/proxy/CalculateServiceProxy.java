package com.zjhuang.netty.proxy;

import com.zjhuang.entity.RpcRequest;
import com.zjhuang.entity.RpcResponse;
import com.zjhuang.netty.client.RpcClient;
import com.zjhuang.service.ICalculateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * 计算服务代理类
 *
 * @author zjhuang
 * @create 2018/11/25 23:08
 **/
public class CalculateServiceProxy implements ICalculateService {

    private Logger logger = LoggerFactory.getLogger(CalculateServiceProxy.class);

    private RpcClient client;

    public CalculateServiceProxy(String host, int port) {
        client = new RpcClient();
        try {
            client.connect(host, port);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public double add(double param1, double param2) {
        RpcRequest request = new RpcRequest();
        request.setId(UUID.randomUUID().toString());
        request.setServiceName(ICalculateService.class.getName());
        request.setMethodName("add");
        request.setParam1(param1);
        request.setParam2(param2);
        RpcResponse response = this.sendRpcRequest(request);
        return response.getResult();
    }

    @Override
    public double sub(double param1, double param2) {
        RpcRequest request = new RpcRequest();
        request.setId(UUID.randomUUID().toString());
        request.setServiceName(ICalculateService.class.getName());
        request.setMethodName("sub");
        request.setParam1(param1);
        request.setParam2(param2);
        RpcResponse response = this.sendRpcRequest(request);
        return response.getResult();
    }

    @Override
    public double mul(double param1, double param2) {
        RpcRequest request = new RpcRequest();
        request.setId(UUID.randomUUID().toString());
        request.setServiceName(ICalculateService.class.getName());
        request.setMethodName("mul");
        request.setParam1(param1);
        request.setParam2(param2);
        RpcResponse response = this.sendRpcRequest(request);
        return response.getResult();
    }

    @Override
    public double div(double param1, double param2) {
        RpcRequest request = new RpcRequest();
        request.setId(UUID.randomUUID().toString());
        request.setServiceName(ICalculateService.class.getName());
        request.setMethodName("div");
        request.setParam1(param1);
        request.setParam2(param2);
        RpcResponse response = this.sendRpcRequest(request);
        return response.getResult();
    }

    private RpcResponse sendRpcRequest(RpcRequest request) {
        RpcResponse response = null;
        try {
            this.client.channel.writeAndFlush(request).sync();
            response = RpcClient.responseQueue.take();
            System.out.println(response);
        } catch (Exception e) {
            logger.error(response.getCause());
        }
        return response;
    }
}
