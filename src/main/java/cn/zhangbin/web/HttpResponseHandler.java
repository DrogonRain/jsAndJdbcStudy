package cn.zhangbin.web;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Map;

public class HttpResponseHandler implements Serializable {

    private static final long serialVersionUID = -8615144921077614489L;

    public static String build(Response response){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(response.getProtocol()).append(" ")
                .append(response.getCode()).append(" ")
                .append(response.getMessage()).append("\r\n");
        // 拼接首部信息
        for(Map.Entry<String,String> entry : response.getHeaders().entrySet()){
            stringBuilder.append(entry.getKey()).append(": ").append(entry.getValue()).append("\r\n");
        }

        stringBuilder.append("\r\n");

        if(response.getBody() != null){
            stringBuilder.append(response.getBody());
        }
        return stringBuilder.toString();
    }

    public static void write(OutputStream outputStream,Response response){
        String message = build(response);
        try{
            outputStream.write(message.getBytes());
            outputStream.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) {
//        Response response = new Response();
//        response.setCode("302");
//        response.setMessage("Moved Temporarily");
//        response.setHeader("Location","https://www.baidu.com");
//        System.out.println(HttpResponseHandler.build(response));
//    }
}
