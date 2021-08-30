package com.smarthardwareshop.api.orders.dto;

import com.smarthardwareshop.api.orders.enums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO for a returned order.
 */
@Getter
@Setter
public class OrderDto {

    /**
     * The order's id.
     */
    @Schema(example = "1")
    private Long id;

    /**
     * The order's creation date.
     */
    @Schema(example = "2021-08-27T21:29:51.248Z")
    private LocalDateTime createdAt;

    /**
     * The order status.
     */
    @Schema(example = "IN_PROGRESS")
    private OrderStatus status;

    /**
     * The order items.
     */
    @Schema
    private List<OrderItemDto> items = new ArrayList<>();
}
