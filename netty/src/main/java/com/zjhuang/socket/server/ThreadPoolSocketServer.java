package com.zjhuang.socket.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池处理socket请求
 *
 * @author zjhuang
 * @create 2018/11/12 19:11
 **/
public class ThreadPoolSocketServer {

    public void startServer() throws IOException {
        final ServerSocket serverSocket = new ServerSocket(8080);
        // 为了代码简洁，这里直接通过工具类创建一个线程池
        ExecutorService executor = Executors.newFixedThreadPool(10);
        System.out.println("Listening for connection on port 8080...");
        while (!Thread.interrupted()) {
            final Socket socket = serverSocket.accept();
            executor.execute(() -> {
                // 1. Read request from the socket of client.
                // 2. Prepare a response.
                // 3. Send response to the client.
                // 4. Close the socket.
            });
        }
    }

    public static void main(String[] args) {

    }
}
