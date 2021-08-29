package com.smarthardwareshop.api.orders.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * DTO for an order that will be saved.
 */
@Getter
@Setter
public class OrderItemSaveDto {

    /**
     * The product associated with this item.
     */
    @NotNull
    @Positive
    @ApiModelProperty(example = "42")
    private Long productId;

    /**
     * The amount of products in this item.
     */
    @NotNull
    @Positive
    @ApiModelProperty(example = "10")
    private int count;
}
