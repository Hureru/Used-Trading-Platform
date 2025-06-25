package com.hureru.design_v2.service;

import com.hureru.design_v2.DTO.CartsAndItemDTO;
import com.hureru.design_v2.service.impl.CartsServiceImpl;
import com.hureru.design_v2.service.impl.CategoriesServiceImpl;
import org.glassfish.jaxb.runtime.v2.runtime.output.SAXOutput;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CartsServiceTest {

    @Autowired
    private CartsServiceImpl cartsService;
    @Test
    public void  getCartsAndItemListByUserIdTest(){
        List<CartsAndItemDTO> cartsAndItemDTOList = cartsService.getCartsAndItemListByUserId(1002);
        cartsAndItemDTOList.forEach(System.out::println);
    }
    @Test
    public void  updateCartsByUserIdAndItemIdTest(){
        System.out.println(cartsService.updateCartsByUserIdAndItemId(1002, 8, true));
    }
    @Test
    public void  updateCartsById(){
        System.out.println(cartsService.updateCartsById(1001, 10));
    }
}
