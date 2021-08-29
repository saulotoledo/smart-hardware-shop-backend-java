package com.smarthardwareshop.api.orders.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO for an order that will be saved.
 */
@Getter
@Setter
public class OrderItemDto {

    /**
     * The product associated with this item.
     */
    @ApiModelProperty(example = "42")
    private Long productId;

    /**
     * The amount of products in this item.
     */
    @ApiModelProperty(example = "10")
    private int count;
}
