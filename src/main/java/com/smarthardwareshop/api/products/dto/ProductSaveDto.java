package com.smarthardwareshop.api.products.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

/**
 * DTO for a product that will be saved.
 */
@Getter
@Setter
public class ProductSaveDto {

    /**
     * The name of the product.
     */
    @NotEmpty
    @Size(min = 4, message = "The product name should have at least 4 characters")
    @Schema(example = "Notebook XPTO 32GB RAM")
    private String name;

    /**
     * The description of the product.
     */
    @NotEmpty
    @Size(min = 20, message = "The product description should have at least 20 characters")
    @Schema(example = "Lorem ipsum sit dolor amet")
    private String description;

    /**
     * The price of the product.
     */
    @NotNull
    @PositiveOrZero
    @Schema(example = "213.2")
    private double price;

    /**
     * The image of the product.
     */
    @Schema(example = "http://www.some-fake-url.com/image.jpg")
    private String image;
}
