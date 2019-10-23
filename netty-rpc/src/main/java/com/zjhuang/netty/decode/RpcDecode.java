package com.zjhuang.netty.decode;

import com.zjhuang.serialize.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * RPC请求数据解码器
 *
 * @author zjhuang
 * @create 2018/11/25 15:00
 **/
public class RpcDecode extends ByteToMessageDecoder {
    /**
     * 泛型类
     */
    private Class<?> genericClass;
    /**
     * 序列化工具
     */
    private Serializer serializer;

    public RpcDecode(Class<?> genericClass, Serializer serializer) {
        this.genericClass = genericClass;
        this.serializer = serializer;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // 先判断buffer中已经接收到的数据长度是否大于4。
        // 因为我们使用的是变长编解码协议，在传输实际的数据包前会先发送一个标识数据长度的整型数据包，
        // 一个整型占4个字节。
        if (in.readableBytes() < 4) {
            return;
        }
        in.markReaderIndex();
        // 读取标识数据长度的数据包
        int idataLength = in.readInt();
        if (idataLength < 0) {
            ctx.close();
        }
        // 判断buffer中新接收的数据包长度是否达到指定长度，没有则继续接收新的数据
        if (in.readableBytes() < idataLength) {
            in.resetReaderIndex();
            return;
        }
        // 当指定长度的数据接收完成后，将完整数据包写入到字节数组中
        byte[] data = new byte[idataLength];
        in.readBytes(data);
        // 将数据字节数组反序列化为目标类型对象
        Object obj = serializer.deserialize(data, genericClass);
        out.add(obj);
    }
}
