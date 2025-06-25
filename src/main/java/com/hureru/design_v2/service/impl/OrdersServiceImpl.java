package com.hureru.design_v2.service.impl;

import com.hureru.design_v2.pojo.Orders;
import com.hureru.design_v2.mapper.OrdersMapper;
import com.hureru.design_v2.service.IOrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements IOrdersService {

}
