package com.hureru.design_v2.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hureru.design_v2.pojo.Item;
import com.hureru.design_v2.service.impl.ItemServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ItemServiceTest {
    @Autowired
    private ItemServiceImpl itemService;

    @Test
    public void getItemListByNameKeyTest() {
        List<Item> itemList = itemService.getItemListByNameKey("子");
        itemList.forEach(System.out::println);
    }

    @Test
    public void getItemListByUserIdTest(){
        List<Item> itemList = itemService.getItemListByUserId(1002);
        itemList.forEach(System.out::println);
    }

    @Test
    public void getItemListByPageTest(){
//        System.out.println("当前页："+page.getCurrent());
//        System.out.println("每页显示的条数："+page.getSize());
//        System.out.println("总记录数："+page.getTotal());
//        System.out.println("总页数："+page.getPages());
//        获取List<Item>: page.getRecords()
        Page<Item> page = itemService.getItemsByPage(1, 20);
        page.getRecords().forEach(System.out::println);
    }
}
