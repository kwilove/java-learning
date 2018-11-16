package com.zjhuang.socket.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 多线程处理socket请求
 *
 * @author zjhuang
 * @create 2018/11/12 19:11
 **/
public class MultiThreadSocketServer {

    public void startServer() throws IOException {
        final ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("Listening for connection on port 8080...");
        while (!Thread.interrupted()) {
            final Socket socket = serverSocket.accept();
            new Thread(() -> {
                // 1. Read request from the socket of client.
                // 2. Prepare a response.
                // 3. Send response to the client.
                // 4. Close the socket.
            }).start();
        }
    }

    public static void main(String[] args) {

    }
}
