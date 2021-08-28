package com.smarthardwareshop.api.products.dto;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(example = "John Doe")
    private String name;

    /**
     * The description of the product.
     */
    @NotEmpty
    @Size(min = 20, message = "The product description should have at least 20 characters")
    @ApiModelProperty(example = "Lorem ipsum sit dolor amet")
    private String description;

    /**
     * The price of the product.
     */
    @NotNull
    @PositiveOrZero
    @ApiModelProperty(example = "213.2")
    private double price;

    /**
     * The image of the product.
     */
    @ApiModelProperty(example = "http://www.some-fake-url.com/image.jpg")
    private String image;
}
