package com.hureru.design_v2.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author zheng
 */
@Data
public class CartsAndItemDTO {
    private Integer cartsId;

    private Integer userId;

    private Long itemId;

    private String itemName;

    private String prices;

    private String imgName;

    private Integer quantity;

    private LocalDateTime addedAt;
}
