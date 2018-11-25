package com.zjhuang.entity;

/**
 * RPC响应
 *
 * @author zjhuang
 * @create 2018/11/24 22:40
 **/
public class RpcResponse {
    /**
     * 响应id
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
     * 处理结果
     */
    private double result;
    /**
     * 处理异常信息
     */
    private String cause;

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

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    @Override
    public String toString() {
        return "RpcResponse{" +
                "id='" + id + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", methodName='" + methodName + '\'' +
                ", result=" + result +
                ", cause='" + cause + '\'' +
                '}';
    }
}
