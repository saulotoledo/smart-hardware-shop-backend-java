package com.smarthardwareshop.api.orders.entities;

import com.smarthardwareshop.api.core.entities.IdentifiableEntity;
import lombok.Data;

import javax.persistence.*;

/**
 * Describes an order.
 */
@Entity
@Table(name = "order_items")
public @Data class OrderItem extends IdentifiableEntity {

    /**
     * The product associated with this item.
     */
    // TODO: Consider adding a relationship here
    @Column(name = "product_id", nullable = false)
    private Long productId;

    /**
     * The amount of products in this item.
     */
    @Column(name = "count", nullable = false)
    private int count;

    /**
     * The order items.
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Order order;
}
