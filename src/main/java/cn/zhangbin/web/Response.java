package cn.zhangbin.web;

import java.io.OutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Response implements Serializable {

    private static final long serialVersionUID = 436338122151368267L;
    // 协议
    private String protocol = "Http/1.1";
    // 响应码
    private String code = "200";
    // 信息
    private String message = "ok";
    // 响应头
    private Map<String,String> header = new HashMap<String,String>();
    // 响应体
    private String body;
    // 响应所携带的流
    private OutputStream outputStream;

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getHeader(String key) {
        return this.header.get(key);
    }

    public void setHeader(String key,String value) {
        this.header.put(key,value);
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Responce{" +
                "protocol='" + protocol + '\'' +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", header=" + header +
                ", body='" + body + '\'' +
                '}';
    }

    public Map<String,String> getHeaders() {
        return this.header;
    }
}
