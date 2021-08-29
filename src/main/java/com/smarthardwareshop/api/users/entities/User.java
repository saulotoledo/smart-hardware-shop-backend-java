package com.smarthardwareshop.api.users.entities;

import com.smarthardwareshop.api.core.entities.IdentifiableTraceableEntity;
import com.smarthardwareshop.api.orders.entities.Order;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The user entity.
 */
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role", discriminatorType = DiscriminatorType.STRING)
public abstract @Data class User extends IdentifiableTraceableEntity {

    /**
     * The user's username.
     */
    @Column(name = "username", length = 50, nullable = false, unique = true)
    private String username;

    /**
     * The user's password.
     */
    @Column(name = "password", length = 60, nullable = false)
    private String password;

    /**
     * Informs if the user is enabled.
     */
    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    /**
     * The order items.
     */
    @OneToMany(mappedBy = "user")
    private List<Order> items = new ArrayList<>();
}
