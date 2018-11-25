package com.zjhuang.netty.registry;

import com.zjhuang.entity.RpcRequest;
import com.zjhuang.entity.RpcResponse;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * RPC服务注册器
 *
 * @author zjhuang
 * @create 2018/11/25 17:07
 **/
public class RpcServiceRegistry {

    private Map<Class<?>, Object> services = new ConcurrentHashMap<>();

    /**
     * 注册/发布RPC服务
     *
     * @param serviceInterface RPC服务接口
     * @param serviceInstance  RPC服务实例
     */
    public void registerService(Class<?> serviceInterface, Object serviceInstance) {
        if (services.containsKey(serviceInterface)) {
            throw new RuntimeException(serviceInterface.getName() + " has been already registered.");
        }
        if (!serviceInterface.isInstance(serviceInstance)) {
            throw new RuntimeException("Service object must implement the service interface " + serviceInterface.getName());
        }
        services.put(serviceInterface, serviceInstance);
    }

    /**
     * 执行RPC服务方法
     *
     * @param rpcRequest RPC请求数据
     * @return RpcResponse RPC响应数据
     */
    public RpcResponse invokeService(RpcRequest rpcRequest) {
        String id = rpcRequest.getId();
        String serviceName = rpcRequest.getServiceName();
        String methodName = rpcRequest.getMethodName();
        double param1 = rpcRequest.getParam1();
        double param2 = rpcRequest.getParam2();

        RpcResponse rpcResponse = new RpcResponse();
        try {
            Class<?> serviceInterface = Class.forName(serviceName);
            Method method = serviceInterface.getMethod(methodName, double.class, double.class);
            Object result = method.invoke(services.get(serviceInterface), param1, param2);
            rpcResponse.setResult((double) result);
        } catch (Exception e) {
            e.printStackTrace();
            rpcResponse.setCause(e.toString());
        } finally {
            rpcResponse.setId(id);
            rpcResponse.setServiceName(serviceName);
            rpcResponse.setMethodName(methodName);
        }
        return rpcResponse;

    }
}
