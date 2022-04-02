package cn.zhangbin.web;

import com.alibaba.fastjson.JSONObject;

public class Test {
    public static void main(String[] args) {
        User user = new User(1,"sas","123");
        String object = JSONObject.toJSONString(user);
        System.out.println(object);
        JSONObject jsonObject = JSONObject.parseObject(object);
        User user1 = JSONObject.toJavaObject(jsonObject, User.class);
        System.out.println(user1.toString());
    }
}
