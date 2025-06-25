package com.hureru.design_v2.service.impl;

import com.hureru.design_v2.pojo.User;
import com.hureru.design_v2.pojo.Role;
import com.hureru.design_v2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zheng
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userService.getUser(username);
        if (user==null){
            throw new UsernameNotFoundException(username);
        }
        System.out.println(user.getUsername());
        List<Role> authorities = userService.getUserAuthority(username);
        // 对用户权限进行封装
        List<SimpleGrantedAuthority> list = authorities.stream().map(authority -> new SimpleGrantedAuthority(authority.getRoleName())).collect(Collectors.toList());
        UserDetails userDetails= new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),list);
        System.out.println(userDetails.getUsername()+userDetails.getAuthorities());
        return userDetails;
    }
}