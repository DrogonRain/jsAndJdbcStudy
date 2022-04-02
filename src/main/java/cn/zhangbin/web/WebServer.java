package cn.zhangbin.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Optional;

public class WebServer {
    public static void main(String[] args) {
        try {
            // 创建并启动一个服务器
            ServerSocket socket = new ServerSocket(8080);
            System.out.println("Tomcat has been started,Default port number is 8080!");
            while (true){
                // 等待客户端接入
                Socket accept = socket.accept();
                // 获取输入流
                InputStream inputStream = accept.getInputStream();
                Request request = HttpRequestHandler.getRequest(inputStream);

                System.out.println(request);


                OutputStream outputStream = accept.getOutputStream();
                Response response = new Response();
                response.setOutputStream(outputStream);

                IOUtil.judgePath(request,response);

                outputStream.close();
                inputStream.close();
                accept.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
