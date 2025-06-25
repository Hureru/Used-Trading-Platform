package com.hureru.design_v2.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.hureru.design_v2.DTO.CartsAndItemDTO;
import com.hureru.design_v2.mapper.CartsMapper;
import com.hureru.design_v2.pojo.Carts;
import com.hureru.design_v2.pojo.Item;
import com.hureru.design_v2.service.ICartsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zheng
 */
@Service
public class CartsServiceImpl extends ServiceImpl<CartsMapper, Carts> implements ICartsService {

    @Autowired
    CartsMapper cartsMapper;

    @Override
    public List<CartsAndItemDTO> getCartsAndItemListByUserId(Integer userId){
        MPJLambdaWrapper<Carts> queryWrapper = new MPJLambdaWrapper<>();
        queryWrapper.eq(Carts::getUserId, userId)
                .selectAll(Carts.class)
                .leftJoin(Item.class, Item::getItemId, Carts::getItemId)
                .selectAll(Item.class);
        return cartsMapper.selectJoinList(CartsAndItemDTO.class, queryWrapper);
    }

    @Override
    public boolean updateCartsByUserIdAndItemId(Integer userId, Integer itemId, boolean flag){
        UpdateWrapper<Carts> updateWrapper= new UpdateWrapper<>();
        updateWrapper.eq("user_id", userId)
                .eq("item_id", itemId);
        if(flag){
            updateWrapper.setSql("quantity = quantity + 1");
        }else{
            updateWrapper.setSql("quantity = quantity - 1");
        }
        return this.update(updateWrapper);
    }

    @Override
    public boolean updateCartsById(Integer cartsId, Integer value){
        UpdateWrapper<Carts> updateWrapper= new UpdateWrapper<>();
        updateWrapper.eq("carts_id", cartsId)
                .set("quantity", value);
        return this.update(updateWrapper);
    }
}
