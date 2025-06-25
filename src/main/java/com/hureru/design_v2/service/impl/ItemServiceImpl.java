package com.hureru.design_v2.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hureru.design_v2.pojo.Item;
import com.hureru.design_v2.mapper.ItemMapper;
import com.hureru.design_v2.service.IItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Hureru
 * @since 2024-11-25
 */
@Service
public class ItemServiceImpl extends ServiceImpl<ItemMapper, Item> implements IItemService {
    @Autowired
    private ItemMapper itemMapper;

    @Override
    @Cacheable(cacheNames = "item", unless = "#result==null")
    public List<Item> getItemListByNameKey (String key){
        if (key == null || key.isEmpty()){
            return this.list();
        }
        QueryWrapper<Item> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("item_name",key)
                .eq("status",1);
        return this.list(queryWrapper);
    }
    @Override
    @Cacheable(cacheNames = "item", unless = "#result==null")
    public List<Item> getItemListByUserId (Integer userId){
        QueryWrapper<Item> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        return this.list(queryWrapper);
    }
    @Override
    public Page<Item> getItemsByPage(Integer pageNo, Integer pageSize){
        Page<Item> page = new Page<>(pageNo, pageSize);
        itemMapper.selectList(page, null);
        return this.page(page);
    }
}
