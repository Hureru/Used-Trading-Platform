package com.hureru.design_v2.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author Hureru
 * @since 2024-11-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("carts")
@ApiModel(value="carts对象", description="")
public class Carts implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "carts_id", type = IdType.AUTO)
    private Integer cartsId;

    private Integer userId;

    private Integer itemId;

    private Integer quantity;

    private LocalDateTime addedAt;
}
