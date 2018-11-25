package com.zjhuang.netty.server;

import com.zjhuang.entity.RpcRequest;
import com.zjhuang.entity.RpcResponse;
import com.zjhuang.netty.decode.RpcDecode;
import com.zjhuang.netty.encode.RpcEncode;
import com.zjhuang.netty.handler.RpcServerHandler;
import com.zjhuang.netty.registry.RpcServiceRegistry;
import com.zjhuang.serialize.Serializer;
import com.zjhuang.serialize.impl.ProtobufSerializer;
import com.zjhuang.service.ICalculateService;
import com.zjhuang.service.impl.CalculateServiceImpl;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 实现接收和处理RPC请求的Server端程序
 *
 * @author zjhuang
 * @create 2018/11/11 17:12
 **/
public class RpcServer {

    Logger logger = LoggerFactory.getLogger(RpcServer.class);

    private final int port;

    private Serializer serializer = new ProtobufSerializer();

    public RpcServer(int port) {
        this.port = port;
    }

    public void init(RpcServiceRegistry rpcServiceRegistry) throws InterruptedException {
        new Thread(() -> {
            try {
                this.start(rpcServiceRegistry);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void start(RpcServiceRegistry rpcServiceRegistry) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast(new RpcDecode(RpcRequest.class, serializer))
                                    .addLast(new RpcEncode(RpcResponse.class, serializer))
                                    .addLast(new RpcServerHandler(rpcServiceRegistry));
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture future = bootstrap.bind(port).sync();
            System.out.println("Netty server socket has started on port " + port);
            future.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 8080;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }

        RpcServiceRegistry rpcServiceRegistry = new RpcServiceRegistry();
        rpcServiceRegistry.registerService(ICalculateService.class, new CalculateServiceImpl());
        new RpcServer(port).init(rpcServiceRegistry);
    }

}
