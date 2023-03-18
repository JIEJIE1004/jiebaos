package com.wz.jiebaos.filter;



import com.wz.jiebaos.pojo.Account;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.*;

@WebFilter(filterName = "myFilter",urlPatterns = "/*")
public class MyFilter  implements Filter {

    //定义不需要用户登录的请求放行
    public static List<String> WHITE_PATH = Arrays.asList("/toFileUp","/","/fileUp",
            "/returnOn","/fileDown","/login");;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponseWrapper wrapper = new HttpServletResponseWrapper((HttpServletResponse)response);
        HttpServletRequest rq = (HttpServletRequest) request;
        HttpServletResponse rp = (HttpServletResponse)response;
        Long account = (Long) rq.getSession().getAttribute("account");
        String requestURI = rq.getRequestURI();

        if(WHITE_PATH.contains(requestURI)){
            //用户无需登录直接通行
            filterChain.doFilter(request,response);
            return;
        }

        //静态资源访问中有static路径，通过判断是否存在静态资源访问放行
        if(requestURI.indexOf("static") != -1)
        {
            filterChain.doFilter(request,response);
            return;
        }

        if(account!=null){
            filterChain.doFilter(request,response);
        } else{
            request.setAttribute("msg","细狗，还没登陆！");
            wrapper.sendRedirect("/");
        }



    }

    @Override
    public void destroy() {

    }
}
