package com.hureru.design_v2.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hureru.design_v2.pojo.User;
import com.hureru.design_v2.pojo.Role;
import com.hureru.design_v2.service.impl.RoleServiceImpl;
import com.hureru.design_v2.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private RoleServiceImpl roleService;

    // 业务控制：使用唯一用户名查询用户信息
    public User getUser(String username){
        User user=null;
        user = userService.findByUsername(username);
        return user;
    }
    // 业务控制：使用唯一用户名查询用户权限
    public List<Role> getUserAuthority(String username){
        List<Role> authorities=null;
        authorities= roleService.findRoleByUsername(username);
        return authorities;
    }
}
