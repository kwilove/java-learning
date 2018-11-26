package com.zjhuang.netty.client;

import com.zjhuang.entity.RpcRequest;
import com.zjhuang.entity.RpcResponse;
import com.zjhuang.netty.decode.RpcDecode;
import com.zjhuang.netty.encode.RpcEncode;
import com.zjhuang.netty.handler.RpcClientHandler;
import com.zjhuang.serialize.Serializer;
import com.zjhuang.serialize.impl.ProtobufSerializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.SynchronousQueue;

/**
 * Echo Client
 *
 * @author zjhuang
 * @create 2018/11/11 17:49
 **/
public class RpcClient {

    public Channel channel;

    private Serializer serializer = new ProtobufSerializer();

    public static final Map<String, RpcClient> clientPool = new ConcurrentHashMap<>();

    public static final Map<String, SynchronousQueue<RpcResponse>> rpcResponseQueues = new ConcurrentHashMap<>();

    /**
     * 建立与目标RPC服务的通道
     *
     * @param host 服务主机地址
     * @param port 服务端口
     * @throws InterruptedException
     */
    public void connect(String host, int port) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) {
                        socketChannel.pipeline()
                                .addLast(new RpcEncode(RpcRequest.class, serializer))
                                .addLast(new RpcDecode(RpcResponse.class, serializer))
                                .addLast(new RpcClientHandler());
                    }
                })
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_REUSEADDR, true)
                .option(ChannelOption.SO_KEEPALIVE, true);
        this.channel = bootstrap.connect(host, port).sync().channel();
    }

    public void close() {
        if (null != this.channel) {
            if (this.channel.isOpen()) {
                this.channel.close();
            }
        }
    }
}
