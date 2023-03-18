package com.wz.jiebaos.controller;

import com.wz.jiebaos.service.FileDownService;
import com.wz.jiebaos.until.Directory;
import com.wz.jiebaos.until.ShowPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.util.ArrayList;

@Controller
public class PageController {
    @Autowired
    private FileDownService fileDownService;
    @RequestMapping("/toChief")
    public String toChief()
    {
        return "chief";
    }

    @RequestMapping("/toCartoon")
    public String toCartoon()
    {
        return "Cartoon";
    }

    @RequestMapping("/toWeb")
    public String toWeb()
    {
        return "Web";
    }

    @RequestMapping("/toLife")
    public String toLife()
    {
        return "Life";
    }

    @RequestMapping("/toFileUp")
    public String toFileUp(Model model,String url,Integer page_now){
        /**
         * 用户跳转至fileup页面
         * 1、用户提交文件表单
         * 2、已上传目录
         */
        if (page_now == null || page_now <= 0)
            page_now = 1;
        if(url == null) {
            url = "/root/work";
        }
        File file = new File(url);
        if(file == null)
        {
            return null;
        }
        ArrayList<File> Directory = fileDownService.getUrlDirectory(url);
        int total_page = Directory.size()/ShowPage.PAGE_SIZE + 1;
        if(page_now>=total_page)
            page_now = total_page;

        Directory directory = new Directory();
        ArrayList<File> urlDirectory = directory.showPage(file,page_now);

        model.addAttribute("urlDirectory",urlDirectory);
        model.addAttribute("url",url);
        model.addAttribute("total",total_page);
        model.addAttribute("page_now",page_now);
        return "fileUp";
    }

    @RequestMapping("/toFileDown")
    public String toFileDown()
    {
        return "redirect:/fileDown";
    }
}
