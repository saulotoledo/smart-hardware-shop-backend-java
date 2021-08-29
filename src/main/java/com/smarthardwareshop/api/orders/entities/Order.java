package com.smarthardwareshop.api.orders.entities;

import com.smarthardwareshop.api.core.entities.IdentifiableEntity;
import com.smarthardwareshop.api.orders.enums.OrderStatus;
import com.smarthardwareshop.api.users.entities.User;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Describes an order.
 */
@Entity
@Table(name = "orders")
@EntityListeners(AuditingEntityListener.class)
public @Data class Order extends IdentifiableEntity {

    /**
     * The order owner.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * The order status.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)
    private OrderStatus status;

    /**
     * The order creation date.
     */
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * The order items.
     */
    @OneToMany(cascade = { CascadeType.ALL }, mappedBy = "order")
    private List<OrderItem> items = new ArrayList<>();
}
