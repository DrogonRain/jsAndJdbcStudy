package cn.zhangbin.web;

import javax.swing.text.html.Option;
import java.io.*;
import java.util.Optional;

public class IOUtil {

//     根据文件地址拿到文件内容
    public static Optional<String> getContext(String path){

        File file = new File(path);
        if (!file.exists()){
            return Optional.empty();
        }

        try (FileInputStream fileInputStream= new FileInputStream(path);){
            StringBuilder stringBuilder = new StringBuilder();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fileInputStream.read(buffer)) != -1){
                stringBuilder.append(new String(buffer,0,len));
            }
            Optional<String> result = Optional.ofNullable(stringBuilder.toString());
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    /**
     * 判断路径是否为访问静态资源
     */
    public static void judgePath(Request request,Response response){
        if (request.getUri().contains(".html") || request.getUri().contains(".jsp")){
            try{
                // 获取资源所在地址
                String path = Constant.ROOT_DIR + request.getUri();
                Optional<String> body = IOUtil.getContext(path);
                if (body.isPresent()){
                    response.setHeader("Content-Type","text/html;charset=UTF-8");
                    response.setHeader("Content-Length",Integer.toString(body.get().getBytes().length));
                    response.setBody(body.get());
                    // 将报文写出给浏览器
                    HttpResponseHandler.write(response.getOutputStream(),response);
                }
                response.getOutputStream().flush();
            }catch (IOException e){
                e.printStackTrace();
            }
        }else{
            Servlet servlet = Container.getServlet(request.getUri());
            if (servlet != null)
                servlet.service(request,response);
        }
    }
}
