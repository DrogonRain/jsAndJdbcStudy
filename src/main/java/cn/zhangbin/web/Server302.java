package cn.zhangbin.web;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server302 {
    public static void main(String[] args) {
        // 创建一个服务器
        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            Socket accept = serverSocket.accept();
            OutputStream outputStream = accept.getOutputStream();
            String responce = "HTTP/1.1 302 Moved Temporarily\r\n" +
                    "Location: https://www.baidu.com\r\n\r\n";
            outputStream.write(responce.getBytes());
            outputStream.close();
            accept.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
