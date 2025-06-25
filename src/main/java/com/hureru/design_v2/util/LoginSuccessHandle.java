package com.hureru.design_v2.util;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Component
public class LoginSuccessHandle implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
        String realpath = "";
        System.out.println(basePath+roles);
        if (roles.contains("user")) {
            System.out.println("用户登录");
            realpath = basePath + "item/showAllItem";
            response.sendRedirect(realpath);
        } else if(roles.contains("admin")){
            System.out.println("管理员登录");
            realpath = basePath + "admin/toManager";
            response.sendRedirect(realpath);
        }
        System.out.println("realpath=="+realpath);
    }
}
