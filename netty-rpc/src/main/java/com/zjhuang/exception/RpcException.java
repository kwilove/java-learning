package com.zjhuang.exception;

/**
 * RPC异常
 *
 * @author zjhuang
 * @create 2018/11/27 13:17
 **/
public class RpcException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    /**
     * 未知异常
     */
    public static final int UNKNOWN_EXCEPTION = 0;
    /**
     * 网络异常
     */
    public static final int NETWORK_EXCEPTION = 1;
    /**
     * 服务超时异常
     */
    public static final int TIMEOUT_EXCEPTION = 2;
    /**
     * 业务处理异常
     */
    public static final int BIZ_EXCEPTION = 3;
    /**
     * 服务被禁止的异常
     */
    public static final int FORBIDDEN_EXCEPTION = 4;
    /**
     * 序列化异常
     */
    public static final int SERIALIZATION_EXCEPTION = 5;
    /**
     * RpcException不能有子类，异常类型用ErrorCode表示，以便保持兼容。
     */
    private int code;

    public RpcException() {
        super();
    }

    public RpcException(String message) {
        super(message);
    }

    public RpcException(Throwable cause) {
        super(cause);
    }

    public RpcException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpcException(int code) {
        super();
        this.code = code;
    }

    public RpcException(String message, int code) {
        super(message);
        this.code = code;
    }

    public RpcException(Throwable cause, int code) {
        super(cause);
        this.code = code;
    }

    public RpcException(String message, Throwable cause, int code) {
        super(message, cause);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isBiz() {
        return code == BIZ_EXCEPTION;
    }

    public boolean isForbidded() {
        return code == FORBIDDEN_EXCEPTION;
    }

    public boolean isTimeout() {
        return code == TIMEOUT_EXCEPTION;
    }

    public boolean isNetwork() {
        return code == NETWORK_EXCEPTION;
    }

    public boolean isSerialization() {
        return code == SERIALIZATION_EXCEPTION;
    }
}
