package com.wz.jiebaos.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wz.jiebaos.pojo.Account;
import com.wz.jiebaos.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class AccountController {
    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String Login(String account,String password, HttpServletRequest request, Model model)
    {
        Account user = new Account();
        try {
            user.setAccount(Long.valueOf(account));
            user.setPassword(password);
        }catch (Exception e)
        {
            model.addAttribute("msg","请输入数字格式账户！");
            return  "index";
        }
        boolean temp = true;
        //用户验证登录信息
        QueryWrapper<Account> wrapper = new QueryWrapper<>();
        wrapper.eq("account",user.getAccount());
        Account one = accountService.getOne(wrapper);
        if (one == null)
            temp = false;
        if (!(temp && one.getPassword().equals(user.getPassword())))
            temp = false;
        if (temp)
        {
            request.getSession().setAttribute("account",user.getAccount());
            return "redirect:/toChief";
        }
        else {
            model.addAttribute("msg","密码或账户输入不正确！");
            return  "index";
        }
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request,Model model)
    {
        //注销session信息
        HttpSession session = request.getSession();
        session.invalidate();
        model.addAttribute("msg","细狗，再见！");
        return "index";
    }
}
