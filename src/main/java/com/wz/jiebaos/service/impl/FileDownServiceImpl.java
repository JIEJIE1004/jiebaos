package com.wz.jiebaos.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wz.jiebaos.enums.Admin;
import com.wz.jiebaos.mapper.UserMapper;
import com.wz.jiebaos.pojo.User;
import com.wz.jiebaos.service.FileDownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;

@Service
public class FileDownServiceImpl implements FileDownService {
    @Autowired
    private UserMapper userMapper;

    //1、检查文件上传者是否存在
    public boolean isMember(String name)
    {
        boolean temp = false;
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name",name);
        if(userMapper.selectOne(wrapper) != null)
            temp = true;
        return temp;
    }

    //2、判断是否有下载权限
    public boolean canDown(String name){
        boolean temp = false;
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name",name);
        temp = userMapper.selectOne(wrapper).getAdmin().equals(Admin.VAR1);
        return temp;
    }

    //3、查找给出路劲下的所有目录和文件
    @Override
    public ArrayList<File> getUrlDirectory(String url) {
        File file = new File(url);
        //判断路劲是否存
        if (!file.exists())
            return null;
        else {      //目录存在，查看，目录下所有的子目录
            ArrayList<File> sonDirectory = new ArrayList<>();
            File[] files = file.listFiles();
            //遍历files，获取到目录名
            for(File f:files)
                sonDirectory.add(f);
            return sonDirectory;
        }
    }

    //4/下载文件
    public boolean fileDown(String url, HttpServletResponse response) throws IOException {
        /**
         * url:包含文件名的地址
         */
        boolean temp = false;
        //获取文件名     lastIndexOf(int ch):最后一次出现的下标
        String fileName = url.substring(url.lastIndexOf('/') + 1);
        //定义file变量，并得到所需下载文件
        File file = new File(url);
        //通过url变量创建输入流
        InputStream in = new BufferedInputStream(new FileInputStream(url)) ;
        //建立输出流
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream()) ;
        //为响应信息添加Content-Disposition元素
        response.addHeader("Content-Disposition","attachment;fileName="+
                URLEncoder.encode(fileName,"utf-8"));
        response.setCharacterEncoding("utf-8"); // 设置相应字符集
        response.setContentType("application/octet-stream");// 设置相应类型
        int data = 0;
        while ((data = in.read()) != -1) {// 通过循环将下载的内容写入下载的路径
            out.write(data);
            temp = true;
        }
        out.close(); // 关闭输出流
        in.close();// 关闭输入流
        return temp;
    }
}
