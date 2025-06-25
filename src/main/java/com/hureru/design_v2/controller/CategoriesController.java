package com.hureru.design_v2.controller;


import com.hureru.design_v2.pojo.Categories;
import com.hureru.design_v2.service.impl.CategoriesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Hureru
 * @since 2024-11-25
 */
@RestController
@RequestMapping("/categories")
public class CategoriesController {
    @Autowired
    private CategoriesServiceImpl categoriesService;
    @PreAuthorize("hasAnyAuthority('user','admin')")
    @PostMapping("/getAllCategories")
    public List<Categories> getAllCategories() {
        return categoriesService.list();
    }
    @PreAuthorize("hasAuthority('admin')")
    @PostMapping("/updateOrAdd")
    public ResponseEntity<String> updateOrAdd(@RequestBody Categories categories) {
        boolean flag = categoriesService.saveOrUpdate(categories);
        return ResponseEntity.ok(flag ? "success" : "fail");
    }
    @PreAuthorize("hasAuthority('admin')")
    @PostMapping("/delete")
    public ResponseEntity<String> delete(@RequestBody Categories categories) {
        boolean flag = categoriesService.removeById(categories);
        return ResponseEntity.ok(flag ? "success" : "fail");
    }
}
