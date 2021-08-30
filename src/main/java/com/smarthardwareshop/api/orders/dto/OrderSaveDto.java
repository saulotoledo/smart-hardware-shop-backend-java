package com.smarthardwareshop.api.orders.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO for an order that will be saved.
 */
@Getter
@Setter
public class OrderSaveDto {

    /**
     * The order items.
     */
    @Valid
    @Schema
    private List<OrderItemSaveDto> items = new ArrayList<>();
}
