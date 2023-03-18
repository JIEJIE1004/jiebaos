package com.wz.jiebaos.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wz.jiebaos.enums.Admin;
import com.wz.jiebaos.pojo.User;
import com.wz.jiebaos.service.FileDownService;
import com.wz.jiebaos.service.UserService;
import com.wz.jiebaos.until.Directory;
import com.wz.jiebaos.until.ShowPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;
import java.util.ArrayList;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private FileDownService fileDownService;

    @RequestMapping(value = "/fileUp",method = RequestMethod.POST)
    public String getFile(MultipartFile upFile, Model model, String name)
    {
        String msg = null;
        //获取上传文件人是否是本班学生
        boolean temp = fileDownService.isMember(name);
        //设置缓存目录
        File f = new File("/root/work");
        //不是本版学生
        if(!temp)
            msg = "名字呐？？？";
        else {
            //文件上传功能
            //获取当前文件上传的文件名字
            String fileName = upFile.getOriginalFilename();
            //将文件保存到一个指定的磁盘目录
            try {
                upFile.transferTo(Paths.get(f + "/" + fileName));
                msg = "感谢大哥，送来作业";
            } catch (Exception e) {
                msg = "文件上传失败";
                e.printStackTrace();
            }
        }
        ArrayList<File> Directory = fileDownService.getUrlDirectory(f.getPath());
        int total_page = Directory.size()/ ShowPage.PAGE_SIZE + 1;
        Directory directory = new Directory();
        ArrayList<File> urlDirectory = directory.showPage(f,1);

        model.addAttribute("urlDirectory",urlDirectory);
        model.addAttribute("url",f.getPath());
        model.addAttribute("total",total_page);
        model.addAttribute("page_now",1);
        model.addAttribute("msg",msg);

        return "fileUp";
    }

    @RequestMapping(value = "/fileDown")
    public String fileDown(Model model, String url,
                           HttpServletResponse response,HttpServletRequest request) throws IOException {
        /**
         *  1.获取当前路径
         *
         */
        String msg = null,path=null;
        File file = new File(url);
        HttpSession session = request.getSession();
        if(!file.exists())
        {
            msg = "查询路劲不存在！";
            path = "/toFileUp";
        }
        else {
            if(file.isDirectory()){

                ArrayList<File> Directory = fileDownService.getUrlDirectory(url);
                int total_page = Directory.size()/ ShowPage.PAGE_SIZE + 1;
                Directory directory = new Directory();
                ArrayList<File> urlDirectory = directory.showPage(file,1);

                model.addAttribute("urlDirectory",urlDirectory);
                model.addAttribute("url",url);
                model.addAttribute("total",total_page);
                model.addAttribute("page_now",1);
               // ArrayList<File> urlDirectory = fileDownService.getUrlDirectory(url);
                path = "fileUp";
//                model.addAttribute("urlDirectory",urlDirectory);
//                model.addAttribute("url",url);

            }else
            {
                msg = "所查看非目录";
                if(session.getAttribute("account")==null)
                {
                    msg = "无下载权限，细狗！";
                    path = "redirect:/toFileUp";
                }
                else {
                    boolean temp = fileDownService.fileDown(url,response);
                    if(!temp) msg = "文件未下载";
                    path = null;
                }
            }
        }
        model.addAttribute("msg",msg);
        return path;
    }

    //返回上一层目录
    @RequestMapping("/returnOn")
    public String return0n(String url, RedirectAttributes attr)
            throws UnsupportedEncodingException {
        String defineUrl = "/root/work";
        if(defineUrl.equals(url))
            return "redirect:/toFileUp";
        if(url.lastIndexOf('/' )!= url.indexOf('/'))
            url = url.substring(0,url.lastIndexOf('/'));
        attr.addAttribute("url",url);
        return "redirect:/toFileUp";
    }


}
