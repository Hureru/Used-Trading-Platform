package com.hureru.design_v2.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hureru.design_v2.pojo.User;
import com.hureru.design_v2.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Hureru
 * @since 2024-11-25
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserServiceImpl userService;

    @PreAuthorize("hasAuthority('user')")
    @GetMapping("toInformation")
    public String toInformation(Integer userId, Model model) {
        User user = userService.getById(userId);
        model.addAttribute("user", user);
        return "user/information";
    }

    @PreAuthorize("hasAnyAuthority('user','admin')")
    @RequestMapping("/getUser")
    public ResponseEntity<User> getUser(@RequestBody Integer userId) {
        User user = userService.getById(userId);
        return ResponseEntity.ok(user);
    }
    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping("/getUsersPage")
    public ResponseEntity<JsonNode> getUsersPage(@RequestBody JsonNode jsonNode) {
        if (jsonNode.isEmpty()) {
            System.out.println("参数为空");
            return null;
        }
        Integer pageNo  = jsonNode.get("pageNo").asInt();
        Integer pageSize  = jsonNode.get("pageSize").asInt();
        Page<User> page = userService.getUserByPage(pageNo, pageSize);
        ObjectNode node = objectMapper.valueToTree(page);
        return ResponseEntity.ok(node);
    }
    @PreAuthorize("hasAnyAuthority('user','admin')")
    @RequestMapping("/update")
    public ResponseEntity<String> update(@RequestBody User user) {
        boolean flag = userService.updateById(user);
        return ResponseEntity.ok(flag ? "success" : "fail");
    }
    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping("/delete")
    public ResponseEntity<String> delete(@RequestBody User user) {
        boolean flag = userService.removeById(user);
        return ResponseEntity.ok(flag ? "success" : "fail");
    }
    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping("/resetPasswd")
    public ResponseEntity<String> resetPasswd(@RequestBody User user) {
        boolean flag = userService.resetPassword(user.getId());
        return ResponseEntity.ok(flag ? "success" : "fail");
    }


    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        // 在无状态的JWT认证中，服务端的退出登录通常不需要做任何特殊处理。
        // 主要的退出逻辑是在客户端清除存储的JWT。
        // 服务端可以返回一个成功的响应，告诉客户端可以进行清理操作了。

        // 将当前请求的token加入到Redis黑名单中。

        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "退出成功");
        return ResponseEntity.ok(result);
    }

}
