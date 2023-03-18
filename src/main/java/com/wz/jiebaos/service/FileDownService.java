package com.wz.jiebaos.service;

import com.wz.jiebaos.enums.Admin;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public interface FileDownService {
    //1、判断上传用户
    public boolean isMember(String name);
    //2、判断是否有下载权限
    public boolean canDown(String name);
    //3、查找给出路劲下的所有目录和文件
    public ArrayList<File> getUrlDirectory(String url);
    public boolean fileDown(String url, HttpServletResponse response) throws IOException;
}
