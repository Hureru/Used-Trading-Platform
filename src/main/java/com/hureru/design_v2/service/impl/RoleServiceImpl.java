package com.hureru.design_v2.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hureru.design_v2.mapper.RoleMapper;
import com.hureru.design_v2.mapper.UserMapper;
import com.hureru.design_v2.pojo.Role;
import com.hureru.design_v2.pojo.User;
import com.hureru.design_v2.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zheng
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
    @Autowired
    private UserServiceImpl userService;
    @Override
    @Cacheable(cacheNames = "role", key = "#username", unless = "#result == null")
    public List<Role> findRoleByUsername(String username){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User user = userService.getOne(queryWrapper);
        QueryWrapper<Role> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("role_id", user.getRoleId());
        return this.list(queryWrapper1);
    }
}
