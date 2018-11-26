package com.zjhuang.netty.handler;

import com.zjhuang.entity.RpcResponse;
import com.zjhuang.netty.client.RpcClient;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zjhuang
 * @create 2018/11/11 17:54
 **/
public class RpcClientHandler extends SimpleChannelInboundHandler<RpcResponse> {

    private Logger logger = LoggerFactory.getLogger(RpcClientHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponse msg) throws Exception {
        RpcClient.rpcResponseQueues.get(msg.getId()).put(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("远程连接异常:", cause);
        ctx.close();
    }
}
