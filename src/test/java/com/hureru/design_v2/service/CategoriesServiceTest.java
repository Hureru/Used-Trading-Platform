package com.hureru.design_v2.service;

import com.hureru.design_v2.service.impl.CategoriesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CategoriesServiceTest {
    @Autowired
    private CategoriesServiceImpl categoriesService;
}
