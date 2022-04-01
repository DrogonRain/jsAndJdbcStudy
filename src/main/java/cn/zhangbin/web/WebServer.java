package cn.zhangbin.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {
    public static void main(String[] args) {
        try {
            // 创建并启动一个服务器
            ServerSocket socket = new ServerSocket(8080);
            while (true){
                // 等待客户端接入
                Socket accept = socket.accept();
                // 获取输入流
                InputStream inputStream = accept.getInputStream();
                // 设置读取字符长度
                byte[] buffer = new byte[1024];
                int len;
                while(inputStream.available() > 0){ // 检查还有多少字节未读取
                    len = inputStream.read(buffer);
                    System.out.print(new String(buffer,0,len));
                }
                // 给浏览器响应
                OutputStream outputStream = accept.getOutputStream();
                // 封装报文
                String response = "HTTP/1.1 200 OK\r\n" +
                        "Content-Length: 39\r\n" +
                        "Content-Type: text/html;charset=UTF-8\r\n\r\n" +
                        "<h1 style=\"color:red\">hello server!</h1>";
                // 将报文写出给浏览器
                outputStream.write(response.getBytes());
                outputStream.flush();
                outputStream.close();
                inputStream.close();
                accept.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
