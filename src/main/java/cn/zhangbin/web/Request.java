package cn.zhangbin.web;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Request implements Serializable {
    private static final long serialVersionUID = -7985046544007898290L;
    // 协议
    private String protocol;
    // 请求方式
    private String type;
    // uri
    private String uri;
    // 请求头
    private Map<String,String> header = new HashMap<String,String>(16);
    // 请求体
    private String body;
    // 获取参数
    private Map<String,String> parameter = new HashMap<>(8);

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getParameter(String key) {
        return parameter.get(key);
    }

    public void setParameter(String key,String value) {
        this.parameter.put(key, value);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
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
        return "Request{" +
                "protocol='" + protocol + '\'' +
                ", type='" + type + '\'' +
                ", uri='" + uri + '\'' +
                ", header=" + header +
                ", body='" + body + '\'' +
                ", parameter=" + parameter +
                '}';
    }
}
