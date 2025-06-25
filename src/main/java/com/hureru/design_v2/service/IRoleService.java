package com.hureru.design_v2.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hureru.design_v2.pojo.Role;

import java.util.List;

/**
 * @author zheng
 */
public interface IRoleService extends IService<Role> {
    List<Role> findRoleByUsername(String username);
}
