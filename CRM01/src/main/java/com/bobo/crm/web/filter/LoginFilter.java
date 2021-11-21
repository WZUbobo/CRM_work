package com.bobo.crm.web.filter;

import com.bobo.crm.settings.domain.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("进入到验证有没有登陆过的过滤器");

        HttpServletRequest request1 = (HttpServletRequest) request;
        HttpServletResponse response1 = (HttpServletResponse) response;

        String  path = request1.getServletPath();

        //不应该被拦截的资源
        if ("/login.jsp".equals(path) || "/settings/user/login.do".equals(path)){

            //放行登陆页面
                chain.doFilter(request,response);
        }else {

            HttpSession session = request1.getSession();
            User user = (User) session.getAttribute("user");

            //如果user不为null,说明登陆过
            if (user!=null) {
                chain.doFilter(request,response);
            }else {

                //重定向到登陆页



                response1.sendRedirect(request1.getContextPath()+"/login.jsp");



            }

        }



    }

    @Override
    public void destroy() {

    }
}
