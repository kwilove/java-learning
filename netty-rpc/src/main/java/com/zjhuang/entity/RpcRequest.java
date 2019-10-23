package com.zjhuang.entity;

/**
 * RPC请求
 *
 * @author zjhuang
 * @create 2018/11/24 22:25
 **/
public class RpcRequest {
    /**
     * 请求id
     */
    private String id;
    /**
     * 请求服务名称：package + class
     */
    private String serviceName;
    /**
     * 请求方法名称
     */
    private String methodName;
    /**
     * 参数1的值
     */
    private double param1;
    /**
     * 参数2的值
     */
    private double param2;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public double getParam1() {
        return param1;
    }

    public void setParam1(double param1) {
        this.param1 = param1;
    }

    public double getParam2() {
        return param2;
    }

    public void setParam2(double param2) {
        this.param2 = param2;
    }

    @Override
    public String toString() {
        return "RpcRequest{" +
                "id='" + id + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", methodName='" + methodName + '\'' +
                ", param1=" + param1 +
                ", param2=" + param2 +
                '}';
    }
}
