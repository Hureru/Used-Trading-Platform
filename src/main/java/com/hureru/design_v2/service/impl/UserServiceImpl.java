package com.hureru.design_v2.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hureru.design_v2.pojo.User;
import com.hureru.design_v2.mapper.UserMapper;
import com.hureru.design_v2.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Hureru
 * @since 2024-11-25
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Override
    public Page<User> getUserByPage(Integer pageNo, Integer pageSize){
        Page<User> page = new Page<>(pageNo, pageSize);
        return this.page(page);
    }
    @Override
    public boolean resetPassword(Long id){
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id)
                .set("password", "$2a$10$/OWjgsRDS6gQLFkuNUSwnuRTZmMUNoYjG3XyBz6lJGKL7QCAdNtia");
        return this.update(updateWrapper);
    }
    @Override
    @Cacheable(cacheNames = "user", unless = "#result==null")
    public User findByUsername (String username){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return this.getOne(queryWrapper);
    }
}
