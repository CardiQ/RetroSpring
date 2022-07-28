package com.example.retrofit;

import com.example.retrofit.mapper.UserMapper;
import jdk.internal.util.xml.impl.Input;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.*;
import java.nio.charset.StandardCharsets;

//@RequestMapping("users")//意为仅users路径下的url可以被响应
@RestController//意为交给SpringBoot管理，同时导包
class MyController {

    @Autowired
    UserMapper userMapper;

    @RequestMapping("get1")
//意为请求路径get1可以触发这个方法
    User get1(Long id) {
        System.out.println("get1 id: " + id);
        User user = userMapper.selectById(id);
        return user;
    }

    @RequestMapping("get2")
    void get2(HttpServletRequest request, HttpServletResponse response) {
        try {

            System.out.println("接收到请求");
            //下载源及内容类型
            String realName = "text2.txt";//文件在客户端的名字
            String path = "D:\\mydisk\\"+realName;//文件在服务器的存储路径
            long fileLength=new File(path).length();
            //response
            String contentType = "application/octet-stream";
            //response.setContentType("text/html;charset=UTF-8");
            request.setCharacterEncoding("UTF-8");
            response.setContentType(contentType);

            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;

            response.setHeader("Content-disposition","attachment;filename="+new String(realName.getBytes("UTF-8"),"ISO8859-1"));
            response.setHeader("Content-Length",String.valueOf(fileLength));
            //开始读出
            bis=new BufferedInputStream(new FileInputStream(path));
            bos=new BufferedOutputStream(response.getOutputStream());
            byte[]buff=new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
            bis.close();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("post1")
//意为请求路径post1可以触发这个方法
    int post1(String username, String password) {
        System.out.println("post1 username: " + username + "password: " + password);
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        int res = userMapper.insert(user);

        return res;
    }

    @RequestMapping("post2")
    @ResponseBody
    public void uploadFile(MultipartFile file) throws Exception {
        try {
            //显示文件名
            String fileName = file.getOriginalFilename();
            System.out.println("前端上传" + fileName);

            //显示文件内容
            StringBuilder content = new StringBuilder();
            BufferedReader readerin = new BufferedReader(new InputStreamReader(file.getInputStream()));
            String linein = "";
            while ((linein = readerin.readLine()) != null) {
                content.append(linein);
            }
            System.out.println(content.toString());

            //保存文件到服务器
            String path = "D:\\mydisk\\";
            File tempFile = new File(path);
            if (!tempFile.exists()) {
                tempFile.mkdir();//是判文件夹是否存在而非文件
            }
            BufferedReader readerout = new BufferedReader(new InputStreamReader(file.getInputStream()));//建立文件
            String lineout = "";
            OutputStream outputStream = new FileOutputStream(tempFile.getPath() + File.separator + fileName);

            while ((lineout = readerout.readLine()) != null) {//？？直接输入碰壁
                outputStream.write(lineout.getBytes(StandardCharsets.UTF_8));
            }
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
