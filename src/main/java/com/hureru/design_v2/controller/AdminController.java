package com.hureru.design_v2.controller;


import com.hureru.design_v2.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Hureru
 * @since 2024-11-25
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserServiceImpl userService;
    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping("/toManager")
    public String toManager(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 获取用户名
        if (authentication != null) {
            String username = authentication.getName();
            Long userId = userService.findByUsername(username).getId();
            model.addAttribute("currentUserId", userId);
        }
        return "admin/manager";
    }
}
