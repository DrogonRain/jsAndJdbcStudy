package cn.zhangbin.web;

import java.io.IOException;
import java.io.InputStream;

public class HttpRequestHandler {

    public static Request getRequest(InputStream inputStream){

        StringBuilder request = new StringBuilder();
        try{
            // 设置读取字符长度
            byte[] buffer = new byte[1024];
            int len;
            do{ // 检查还有多少字节未读取
                len = inputStream.read(buffer);
                request.append(new String(buffer,0,len));
            }while(inputStream.available() > 0);
        }catch (IOException e){
            e.printStackTrace();
        }

        Request httpRequest = new Request();
        String[] headAndBody = request.toString().split("\r\n\r\n");
        // 先处理实体
        if (headAndBody.length > 1 && headAndBody[1] != null){
            httpRequest.setBody(headAndBody[1]);
        }
        // 处理请求行
        String startAndHeader = headAndBody[0];
        String[] startAndHeaders = startAndHeader.split("\r\n");
        String startLine = startAndHeaders[0];
        String[] startLineElement = startLine.split(" ");
        httpRequest.setType(startLineElement[0]);
        // uri当中可能包含参数, 处理参数
        if (startLineElement[1].contains("?")){
            String[] uriAndParam = startLineElement[1].split("\\?");
            httpRequest.setUri(uriAndParam[0]);
            if (uriAndParam.length > 1 && uriAndParam[1] != null){
                String[] parameters = uriAndParam[1].split("&");
                for (String parameter : parameters) {
                    String[] keyAndValue = parameter.split("=");
                    httpRequest.setParameter(keyAndValue[0], keyAndValue[1]);
                }
            }
        }else{
            httpRequest.setUri(startLineElement[1]);
        }
        httpRequest.setProtocol(startLineElement[2]);
        // 处理首部信息,startAndHeaders除了第一行其余的部分
        for (int i = 1; i < startAndHeaders.length; i++) {
            String[] keyAndValue = startAndHeaders[i].split(": ");
            httpRequest.setHeader(keyAndValue[0],keyAndValue[1]);
        }
        return httpRequest;
    }
}
