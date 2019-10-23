package com.zjhuang.netty.handler;

import com.zjhuang.entity.RpcRequest;
import com.zjhuang.netty.registry.RpcServiceRegistry;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author zjhuang
 * @create 2018/11/11 17:23
 **/
public class RpcServerHandler extends SimpleChannelInboundHandler<RpcRequest> {

    private RpcServiceRegistry rpcServiceRegistry;

    public RpcServerHandler(RpcServiceRegistry rpcServiceRegistry) {
        this.rpcServiceRegistry = rpcServiceRegistry;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest msg) throws Exception {
        ctx.writeAndFlush(rpcServiceRegistry.invokeService(msg));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
