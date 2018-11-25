package com.zjhuang.netty.encode;

import com.zjhuang.serialize.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * RPC响应对象编码器
 *
 * @author zjhuang
 * @create 2018/11/25 15:04
 **/
public class RpcEncode extends MessageToByteEncoder {
    /**
     * 泛型类
     */
    private Class<?> genericClass;
    /**
     * 序列化工具
     */
    private Serializer serializer;

    public RpcEncode(Class<?> genericClass, Serializer serializer) {
        this.genericClass = genericClass;
        this.serializer = serializer;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        // 先判断响应对象是否是指定类型的实例
        if (genericClass.isInstance(msg)) {
            // 序列化响应对象
            byte[] bytes = serializer.serialize(msg);
            // 发送序列化后的响应数据长度
            out.writeInt(bytes.length);
            // 发送序列化后的响应数据
            out.writeBytes(bytes);
        }
    }
}
