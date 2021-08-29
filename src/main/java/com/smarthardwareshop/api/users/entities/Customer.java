package com.smarthardwareshop.api.users.entities;

import com.smarthardwareshop.api.orders.entities.Order;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@DiscriminatorValue(value = "CUSTOMER")
public class Customer extends User {

    /**
     * The customer's past orders.
     */
    @OneToMany(mappedBy="user")
    private List<Order> orders = new ArrayList<>();
}
