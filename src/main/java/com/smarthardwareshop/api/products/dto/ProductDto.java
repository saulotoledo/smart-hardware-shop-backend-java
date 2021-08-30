package com.smarthardwareshop.api.products.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * DTO for a returned product.
 */
@Getter
@Setter
public class ProductDto extends ProductUpdateDto {

    /**
     * The product's id.
     */
    @Schema(example = "1")
    private Long id;

    /**
     * The product's creation date.
     */
    @Schema(example = "2021-08-27T21:29:51.248Z")
    private LocalDateTime createdAt;

    /**
     * The product's last modified date.
     */
    @Schema(example = "2021-08-27T21:29:51.248Z")
    private LocalDateTime updatedAt;
}
