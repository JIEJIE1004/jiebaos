package com.wz.jiebaos.until;

import com.wz.jiebaos.service.FileDownService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Directory {

    public void test()
    {
        System.out.println("git测试");
    }

    //获取到默认根目录
    public ArrayList<File> getRootDirectory(File file)
    {
        ArrayList<File> sonDirectory = new ArrayList<>();
        File[] files = file.listFiles();
        //遍历files，获取到目录名
        for(File f:files)
            sonDirectory.add(f);
        return sonDirectory;
    }

    //分页显示工具
    public ArrayList<File> showPage(File file,Integer page_now)
    {
        ArrayList<File> Directory = new ArrayList<>();
        File[] files = file.listFiles();
        //遍历files，获取到目录名
        for(File f:files)
            Directory.add(f);
        ArrayList<File> urlDirectory = new ArrayList<File>();

        //每页存放ShowPage.PAGE_SIZE条数据
        for(int i = ( page_now - 1) * 10; i < ShowPage.PAGE_SIZE + ( page_now - 1) * 10;i++)
        {
            if(i<Directory.size())
                urlDirectory.add(Directory.get(i));
        }
        return urlDirectory;
    }
}
