package com.zjhuang.socket.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * 实现Java NIO处理socket请求
 *
 * @author zjhuang
 * @create 2018/11/20 19:21
 **/
public class NioSocketServer implements Runnable {

    /**
     * 选择器
     */
    private Selector selector;
    /**
     * Server端Socket通道
     */
    private ServerSocketChannel channel;

    public NioSocketServer(int port) throws IOException {
        // 创建选择器
        selector = Selector.open();
        // 打开一个server端socket通道
        channel = ServerSocketChannel.open();
        // 设置为non-blocking模式
        channel.configureBlocking(false);
        // 通道绑定到指定端口
        channel.socket().bind(new InetSocketAddress(port), 1024);
        // 将通道注册到selector上，并且对Accept事件感兴趣
        channel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("正在监听" + port + "端口請求...");
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                // 检查就绪的通道
                if (selector.select(1000) == 0) {
                    continue;
                }
                // 返回就绪通道集合
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                // 遍历集合
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    handle(key);
                    // 移除已处理通道
                    iterator.remove();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (null != selector) {
            try {
                // 关闭Selector，并使注册到该Selector上的所有SelectionKey实例无效
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 处理通道的读写事件
     *
     * @param key SelectionKey对象
     * @throws IOException
     */
    private void handle(SelectionKey key) throws IOException {
        // 判断新接入的通道是否有效
        if (key.isValid()) {
            if (key.isAcceptable()) {
                // 当通道就绪时注册一个新的socketChannel，并对读取事件感兴趣
                ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                SocketChannel socketChannel = serverSocketChannel.accept();
                socketChannel.configureBlocking(false);
                socketChannel.register(selector, SelectionKey.OP_READ);
            }
            if (key.isReadable()) {
                SocketChannel socketChannel = (SocketChannel) key.channel();
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                int readBytes = socketChannel.read(readBuffer);
                if (readBytes == -1) {
                    socketChannel.close();
                    key.cancel();
                } else {
                    // 切换到读模式
                    readBuffer.flip();
                    // 创建一个readBuffer钟剩余数据大小的字节数组
                    byte[] bytes = new byte[readBuffer.remaining()];
                    // 从readBuffer中读取指定大小的数据到字节数组中
                    readBuffer.get(bytes);
                    // 打印接收到的数据
                    System.out.println("服务端接收到数据：" + new String(bytes, "UTF-8"));
                    // 回显数据
                    socketChannel.write(ByteBuffer.wrap("success\r\n".getBytes()));
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        new Thread(new NioSocketServer(8080)).start();
    }

}
