package cn.zhangbin.web;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

public class UserServlet implements Servlet{
    @Override
    public void service(Request request, Response response) {
        try{
            // 获取参数中的id的值并转为整型
            Integer id = Integer.parseInt(request.getParameter("id"));
            UserDao userDao = new UserDao();
            User user = userDao.findUserById(id);
            JSONObject object = JSONObject.parseObject(user.toString());
            if (object != null){
                // 设置前段响应文本内容
                response.setHeader("Content-Type","text/plain;charset=UTF-8");
                response.setHeader("Content-Length",Integer.toString(object.toString().getBytes().length));
                response.setBody(object.toString());
                // 将报文写出给浏览器
                HttpResponseHandler.write(response.getOutputStream(),response);
            }
            response.getOutputStream().flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
