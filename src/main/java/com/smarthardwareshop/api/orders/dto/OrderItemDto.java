package com.smarthardwareshop.api.orders.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(example = "42")
    private Long productId;

    /**
     * The amount of products in this item.
     */
    @Schema(example = "10")
    private int count;
}
