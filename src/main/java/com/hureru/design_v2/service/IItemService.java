package com.hureru.design_v2.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hureru.design_v2.pojo.Item;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Hureru
 * @since 2024-11-25
 */
public interface IItemService extends IService<Item> {
    List<Item> getItemListByNameKey (String key);
    List<Item> getItemListByUserId (Integer userId);
    Page<Item> getItemsByPage(Integer pageNo, Integer pageSize);
}
