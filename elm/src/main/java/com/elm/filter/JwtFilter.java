package com.elm.filter;

import com.elm.utils.JwtUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@WebFilter("/api/**")
public class JwtFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        //在这里判断token值即可
        System.out.println("12121212");

        try {
            HttpServletRequest request=(HttpServletRequest)servletRequest;
            HttpServletResponse response= (HttpServletResponse) servletResponse;

            String token=request.getHeader("token");
            String long_token=request.getHeader("long_token");
            int res=0;
            try {
                res=JwtUtils.istoken(token,long_token);
            }catch (Exception e)
            {
                System.out.println("过期了,所以不执行");
                e.printStackTrace();
                System.out.println(res);
                throw e;
            }
            if(res==0)
            {
                //token已经过期
                response.setStatus(401);
                response.sendRedirect("login.html");
//                response.getWriter().write("登录过期，请重试");
            }
            else if(res==1)
            {
//            短的过期了需要刷新
                String userid=JwtUtils.getUseridBytoken(request.getHeader("long_token"));
                int isadmin=JwtUtils.getIsStoreByToken(request.getHeader("long_token"));

                Map<String,String> map=JwtUtils.getToken(userid,isadmin);
                response.setHeader("token", (String) map.get("token"));
                response.setHeader("long_token", (String) map.get("long_token"));

                filterChain.doFilter(request,response);
            }
            else
            {
//            继续执行操作
                filterChain.doFilter(request,response);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {

    }

}

