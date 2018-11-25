package com.zjhuang.netty.client;

import com.zjhuang.entity.RpcRequest;
import com.zjhuang.entity.RpcResponse;
import com.zjhuang.netty.decode.RpcDecode;
import com.zjhuang.netty.encode.RpcEncode;
import com.zjhuang.netty.handler.RpcClientHandler;
import com.zjhuang.netty.proxy.CalculateServiceProxy;
import com.zjhuang.serialize.Serializer;
import com.zjhuang.serialize.impl.ProtobufSerializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

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

    public static SynchronousQueue<RpcResponse> responseQueue = new SynchronousQueue<>();

    public void connect(String host, int port) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast(new RpcEncode(RpcRequest.class, serializer))
                                    .addLast(new RpcDecode(RpcResponse.class, serializer))
                                    .addLast(new RpcClientHandler());
                        }
                    })
                    .option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.SO_REUSEADDR, true)
                    .option(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture future = bootstrap.connect(host, port).sync();
            this.channel = future.channel().closeFuture().sync().channel();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        String host = "127.0.0.1";
        int port = 8080;
        new CalculateServiceProxy(host, port).add(1.0, 2.0);
    }
}
