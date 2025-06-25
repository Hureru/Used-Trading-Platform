package com.hureru.design_v2.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.hureru.design_v2.DTO.CartsAndItemDTO;
import com.hureru.design_v2.pojo.Carts;

import java.util.List;

/**
 * @author zheng
 */
public interface ICartsService extends IService<Carts> {
    List<CartsAndItemDTO> getCartsAndItemListByUserId(Integer userId);
    boolean updateCartsByUserIdAndItemId(Integer userId, Integer itemId, boolean flag);
    boolean updateCartsById(Integer cartsId, Integer value);
}
