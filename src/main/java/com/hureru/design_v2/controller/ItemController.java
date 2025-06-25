package com.hureru.design_v2.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hureru.design_v2.pojo.Categories;
import com.hureru.design_v2.pojo.Item;
import com.hureru.design_v2.service.impl.CategoriesServiceImpl;
import com.hureru.design_v2.service.impl.ItemServiceImpl;
import com.hureru.design_v2.service.impl.UserServiceImpl;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Hureru
 * @since 2024-11-25
 */
@Controller
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ItemServiceImpl itemService;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private CategoriesServiceImpl categoriesService;
    private final String filePath =  "D:\\Code\\Javacode\\Spring\\Design_v2\\target\\classes\\static\\upload";

    @PreAuthorize("hasAnyAuthority('user','admin')")
    @GetMapping("/showAllItem")
    public String showItem(String key, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 获取用户名
        if (authentication != null) {
            String username = authentication.getName();
            Long userId = userService.findByUsername(username).getId();
            model.addAttribute("currentUserId", userId);
        }
        List<Item> itemList = itemService.getItemListByNameKey(key);
        model.addAttribute("pageItems", itemList);
        model.addAttribute("key", key);
        return "user/home";
    }
    @PreAuthorize("hasAnyAuthority('user','admin')")
    @GetMapping("/showMyItem")
    public String showMyItem(Integer userId, Model model) {
        List<Item> itemList = itemService.getItemListByUserId(userId);
        model.addAttribute("pageItems", itemList);
        List<Categories> categoriesList = categoriesService.list();
        model.addAttribute("currentUserId", userId);
        model.addAttribute("categoriesList", categoriesList);
        return "user/itemList";
    }
    @PreAuthorize("hasAuthority('admin')")
    @PostMapping("/getItemsPage")
    @ResponseBody
    public ResponseEntity<JsonNode> getItemsPage(@RequestBody JsonNode jsonNode) {
        if (jsonNode.isEmpty()) {
            System.out.println("参数为空");
            return null;
        }
        Integer pageNo  = jsonNode.get("pageNo").asInt();
        Integer pageSize  = jsonNode.get("pageSize").asInt();
        Page<Item> page = itemService.getItemsByPage(pageNo, pageSize);
        ObjectNode node = objectMapper.valueToTree(page);
        return ResponseEntity.ok(node);
    }
    @PreAuthorize("hasAnyAuthority('user','admin')")
    @GetMapping("/addItem")
    public String addItem(Integer userId, Model model) {
        List<Categories> categoriesList = categoriesService.list();
        model.addAttribute("categoriesList", categoriesList);
        model.addAttribute("currentUserId", userId);
        return "user/addItem";
    }
    @PreAuthorize("hasAnyAuthority('user','admin')")
    @PostMapping("/update")
    public ResponseEntity<String> updateItem(@RequestBody Item item) {
        boolean flag = itemService.updateById(item);
        return ResponseEntity.ok(flag ? "success" : "fail");
    }
    @PreAuthorize("hasAnyAuthority('user','admin')")
    @PostMapping("/delete")
    public ResponseEntity<String> deleteItem(@RequestBody Item item) {
        boolean flag = itemService.removeById(item);
        return ResponseEntity.ok(flag ? "success" : "fail");
    }
    @PreAuthorize("hasAuthority('admin')")
    @PostMapping("/multipleDelete")
    public ResponseEntity<String> multipleDelete(@RequestBody List<Item> itemList) {
        boolean flag = itemService.removeByIds(itemList);
        return ResponseEntity.ok(flag ? "success" : "fail");
    }
    @PreAuthorize("hasAnyAuthority('user','admin')")
    @PostMapping("/add")
    public ResponseEntity<String> addItem(@RequestBody Item item) {
        boolean flag = itemService.save(item);
        return ResponseEntity.ok(flag ? "success" : "fail");
    }

    @PreAuthorize("hasAnyAuthority('user','admin')")
    @PostMapping("/uploadFile")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("未上传文件");}
        // 检查文件类型
        String contentType = file.getContentType();
        if (!"image/jpeg".equals(contentType) && !"image/png".equals(contentType)) {
            return ResponseEntity.badRequest().body("仅支持 JPG 和 PNG 格式图片");}
        // 检查文件大小
        if (file.getSize() > 2 * 1024 * 1024) {
            return ResponseEntity.badRequest().body("文件大小不能超过 2MB");}
        try {
            // 确保上传目录存在
            Path path = Paths.get(filePath);
            if (!Files.exists(path)) {
                Files.createDirectories(path);}
            // 生成唯一文件名，保留原始扩展名
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String fileName = UUID.randomUUID().toString() + fileExtension;
            // 保存文件
            Path filePath = path.resolve(fileName);
            file.transferTo(filePath.toFile());
            // 返回文件访问路径
            return ResponseEntity.ok("/uploads/" + fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("文件上传失败");
        }
    }

    @PreAuthorize("hasAnyAuthority('user','admin')")
    @GetMapping("/download")
    public ResponseEntity<?> downloadFile(@RequestParam String filename) {
        String fileBasePath = filePath + "\\";
        try {
            // 拼接文件路径
            Path filePath = Paths.get(fileBasePath).resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            // 检查文件是否存在且可读
            if (!resource.exists() || !resource.isReadable()) {
                System.out.println("文件不存在或无法读取: " + filename);
                return ResponseEntity.internalServerError().body("文件不存在或无法读取: " + filename);
            }
            // 设置响应头
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (Exception e) {
            System.out.println(e.toString());
            return ResponseEntity.internalServerError().body("下载文件失败: " + filename);
        }
    }
}
