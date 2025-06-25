package com.hureru.design_v2.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hureru.design_v2.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Hureru
 * @since 2024-11-25
 */
public interface IUserService extends IService<User> {
    Page<User> getUserByPage(Integer pageNo, Integer pageSize);

    boolean resetPassword(Long id);

    User findByUsername (String username);
}
