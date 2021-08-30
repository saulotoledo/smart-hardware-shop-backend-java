package com.smarthardwareshop.api.products.controllers;

import com.smarthardwareshop.api.core.annotations.*;
import com.smarthardwareshop.api.products.dto.ProductDto;
import com.smarthardwareshop.api.products.dto.ProductSaveDto;
import com.smarthardwareshop.api.products.dto.ProductUpdateDto;
import com.smarthardwareshop.api.products.entities.Product;
import com.smarthardwareshop.api.products.services.ProductsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

/**
 * Products REST controller.
 */
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Tag(name = "Products")
public class ProductsController {

    /**
     * Model mapper used to transform entities from/to DTOs.
     */
    private final ModelMapper modelMapper;

    /**
     * The product service.
     */
    private final ProductsService service;

    /**
     * Returns many items.
     *
     * @param filter A filter for the items.
     * @param pageable Pageable object build by Spring from the attributes page, size and sort.
     * @return A page of items.
     */
    @SwaggerGetMany
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ProductDto> getMany(@RequestParam(required = false) String filter, Pageable pageable) {
        Page<Product> productsPage = this.service.getMany(filter, pageable);
        return productsPage.map(product -> modelMapper.map(product, ProductDto.class));
    }

    /**
     * Returns a single item.
     *
     * @param id The ID of the item to return.
     * @return The requested item.
     * @throws ResponseStatusException If the informed item was not found.
     */
    @SwaggerGetOne
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductDto getOne(
        @Param("Id of the resource to be obtained.")
        @PathVariable("id") Long id
    ) throws ResponseStatusException {
        return modelMapper.map(
            this.service.getOne(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The entity was not found")
            ),
            ProductDto.class
        );
    }

    /**
     * Saves an item.
     *
     * @param dto The DTO of the item to save.
     * @return The saved item.
     */
    @SwaggerSave
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductDto save(
        @Param("JSON representation of the resource to be saved.")
        @RequestBody @Valid ProductSaveDto dto
    ) {
        Product savedProduct = this.service.save(modelMapper.map(dto, Product.class));
        return modelMapper.map(savedProduct, ProductDto.class);
    }

    /**
     * Updates an item or creates a new item if the informed item does not exist.
     *
     * @param id The id of the item to update.
     * @param dto The DTO of the item to update.
     * @return The updated item.
     */
    @SwaggerUpdate
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDto> update(
        @Param("Id of the resource to be updated or created.")
        @PathVariable("id") Long id,
        @Param("JSON representation of the resource to be saved.")
        @RequestBody @Valid ProductUpdateDto dto
    ) {
        HttpStatus resultStatus = HttpStatus.OK;

        Product productToSave = modelMapper.map(dto, Product.class);
        if (this.service.exists(id)) {
            productToSave.setId(id);
            resultStatus = HttpStatus.CREATED;
        }

        Product savedProduct = this.service.save(productToSave);
        return new ResponseEntity<>(modelMapper.map(savedProduct, ProductDto.class), resultStatus);
    }

    /**
     * Deletes an item.
     *
     * @param id The id of the item do delete.
     * @throws ResponseStatusException If the informed item was not found.
     */
    @SwaggerDelete
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(
        @Param("Id of the resource to be deleted.")
        @PathVariable("id") Long id
    ) throws ResponseStatusException {
        /*
          The DELETE section of the RFC 7231 (https://tools.ietf.org/html/rfc7231#section-4.3.5)
          does not comment on what should be done if the resource was not found. Considering that
          DELETE should be idempotent, we should return "NO_CONTENT" regardless the resource was
          found or not. However, we decided to inform the user that the resource was not found below.
        */
        if (!this.service.exists(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The entity was not found");
        }
        this.service.delete(id);
    }
}
