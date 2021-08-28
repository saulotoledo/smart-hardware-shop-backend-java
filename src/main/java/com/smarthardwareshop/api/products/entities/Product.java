package com.smarthardwareshop.api.products.entities;

import com.smarthardwareshop.api.core.entities.IdentifiableTraceableEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Describes a product.
 */
@Entity
@Table(name = "products")
public @Data class Product extends IdentifiableTraceableEntity {

    /**
     * The name of the product.
     */
    @Column(name = "name", length = 150, nullable = false)
    private String name;

    /**
     * The description of the product.
     */
    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    private String description;

    /**
     * The price of the product.
     */
    @Column(name = "price", nullable = false)
    private double price;

    /**
     * The image of the product.
     */
    @Column(name = "image")
    private String image;
}
