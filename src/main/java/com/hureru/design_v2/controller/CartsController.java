package com.hureru.design_v2.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hureru.design_v2.DTO.CartsAndItemDTO;
import com.hureru.design_v2.pojo.Carts;
import com.hureru.design_v2.service.impl.CartsServiceImpl;
import org.glassfish.jaxb.runtime.v2.runtime.output.SAXOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author zheng
 */
@Controller
@RequestMapping("/carts")
public class CartsController {
    @Autowired
    private CartsServiceImpl cartsService;

    @PreAuthorize("hasAnyAuthority('user','admin')")
    @GetMapping("/{userId}")
    public String carts(@PathVariable("userId") Integer userId, Model model) {
        List<CartsAndItemDTO> cartsAndItemDTOList = cartsService.getCartsAndItemListByUserId(userId);
        model.addAttribute("currentUserId", userId);
        model.addAttribute("cartsAndItemDTOList", cartsAndItemDTOList);
        return "user/carts";
    }
    @PreAuthorize("hasAnyAuthority('user','admin')")
    @PostMapping("/delete")
    public ResponseEntity<String> delete(@RequestBody JsonNode jsonNode) {
        Integer cartsId = jsonNode.get("cartsId").asInt();
        boolean flag = cartsService.removeById(cartsId);
        System.out.println(cartsId);
        return ResponseEntity.ok(flag ? "success" : "fail");
    }
    @PreAuthorize("hasAnyAuthority('user','admin')")
    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody JsonNode jsonNode) {
        Integer itemId = jsonNode.get("itemId").asInt();
        Integer userId = jsonNode.get("userId").asInt();
        Carts carts = new Carts();
        carts.setItemId(itemId);
        carts.setUserId(userId);
        carts.setQuantity(1);
        try {
            cartsService.save(carts);
        }catch (DuplicateKeyException e){
            boolean flag2 = cartsService.updateCartsByUserIdAndItemId(userId, itemId, true);
            return ResponseEntity.ok(flag2 ? "success" : "fail");
        }
        return ResponseEntity.ok("success");
    }
    @PreAuthorize("hasAnyAuthority('user','admin')")
    @PostMapping("/plusOrMinus")
    public ResponseEntity<String> plusOrMinus(@RequestBody JsonNode jsonNode) {
        Integer cartsId = jsonNode.get("cartsId").asInt();
        Integer value = jsonNode.get("value").asInt();
        boolean flag = cartsService.updateCartsById(cartsId, value);
        return ResponseEntity.ok(flag ? "success" : "fail");
    }
}
