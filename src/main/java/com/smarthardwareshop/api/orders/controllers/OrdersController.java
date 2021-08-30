package com.smarthardwareshop.api.orders.controllers;

import com.smarthardwareshop.api.core.annotations.*;
import com.smarthardwareshop.api.orders.dto.OrderDto;
import com.smarthardwareshop.api.orders.dto.OrderSaveDto;
import com.smarthardwareshop.api.orders.dto.OrderUpdateDto;
import com.smarthardwareshop.api.orders.entities.Order;
import com.smarthardwareshop.api.orders.entities.OrderItem;
import com.smarthardwareshop.api.orders.enums.OrderStatus;
import com.smarthardwareshop.api.orders.services.OrdersService;
import com.smarthardwareshop.api.products.entities.Product;
import com.smarthardwareshop.api.products.services.ProductsService;
import com.smarthardwareshop.api.users.entities.Admin;
import com.smarthardwareshop.api.users.entities.User;
import com.smarthardwareshop.api.users.services.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Orders REST controller.
 */
@RestController
@RequestMapping("/users/{userId}/orders")
@RequiredArgsConstructor
@Tag(name = "Orders")
public class OrdersController {

    /**
     * Model mapper used to transform entities from/to DTOs.
     */
    private final ModelMapper modelMapper;

    /**
     * The orders service.
     */
    private final OrdersService ordersService;

    /**
     * The orders service.
     */
    private final UsersService usersService;

    /**
     * The orders service.
     */
    private final ProductsService productsService;

    /**
     * Returns many items.
     *
     * @param userId Id of the owner.
     * @param pageable Pageable object build by Spring from the attributes page, size and sort.
     * @return A page of items.
     */
    @SwaggerGetMany
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<OrderDto> getMany(
        @PathVariable("userId") Long userId,
        Pageable pageable
    ) {
        this.validateCustomer(userId);

        Page<Order> ordersPage = this.ordersService.getMany(userId, pageable);
        return ordersPage.map(order -> modelMapper.map(order, OrderDto.class));
    }

    /**
     * Returns a single item.
     *
     * @param userId Id of the owner.
     * @param id The ID of the item to return.
     * @return The requested item.
     * @throws ResponseStatusException If the informed item was not found.
     */
    @SwaggerGetOne
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderDto getOne(
        @PathVariable("userId") Long userId,
        @Parameter(description = "Id of the resource to be obtained.")
        @PathVariable("id") Long id
    ) throws ResponseStatusException {
        this.validateCustomer(userId);

        return modelMapper.map(
            this.ordersService.getOne(id, userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The entity was not found")
            ),
            OrderDto.class
        );
    }

    /**
     * Saves an item.
     *
     * @param userId Id of the owner.
     * @param dto The DTO of the item to save.
     * @return The saved item.
     * @throws ResponseStatusException If the user was not found.
     */
    @SwaggerSave
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderDto save(
        @PathVariable("userId") Long userId,
        @Parameter(description = "JSON representation of the resource to be saved.")
        @RequestBody @Valid OrderSaveDto dto
    ) throws ResponseStatusException {
        this.validateCustomer(userId);

        Order orderToSave = this.convertOrderDtoToOrder(dto);
        orderToSave.setUser(this.getUserById(userId));
        Order savedOrder = this.ordersService.save(orderToSave);
        return modelMapper.map(savedOrder, OrderDto.class);
    }

    /**
     * Updates an item or creates a new item if the informed item does not exist.
     *
     * @param userId Id of the owner.
     * @param id The id of the item to update.
     * @param dto The DTO of the item to update.
     * @return The updated item.
     * @throws ResponseStatusException If the user was not found.
     */
    @SwaggerUpdate
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderDto> update(
        @PathVariable("userId") Long userId,
        @Parameter(description = "Id of the resource to be updated or created.")
        @PathVariable("id") Long id,
        @Parameter(description = "JSON representation of the resource to be saved.")
        @RequestBody @Valid OrderUpdateDto dto
    ) {
        this.validateCustomer(userId);

        HttpStatus resultStatus = HttpStatus.OK;

        // TODO: Refactor behavior below by using ModelMapper.
        // The following line relies on OrderUpdateDto extending OrderSaveDto. We should not depend on that.
        Order orderToSave = this.convertOrderDtoToOrder(dto);

        orderToSave.setUser(this.getUserById(userId));
        if (this.ordersService.exists(id, userId)) {
            orderToSave.setId(id);
            resultStatus = HttpStatus.CREATED;
        }

        Order savedOrder = this.ordersService.save(orderToSave);
        return new ResponseEntity<>(modelMapper.map(savedOrder, OrderDto.class), resultStatus);
    }

    /**
     * Deletes an item.
     *
     * @param userId Id of the owner.
     * @param id The id of the item to delete.
     * @throws ResponseStatusException If the informed item was not found.
     */
    @SwaggerDelete
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(
        @PathVariable("userId") Long userId,
        @Parameter(description = "Id of the resource to be deleted.")
        @PathVariable("id") Long id
    ) throws ResponseStatusException {
        this.validateCustomer(userId);

        /*
          The DELETE section of the RFC 7231 (https://tools.ietf.org/html/rfc7231#section-4.3.5)
          does not comment on what should be done if the resource was not found. Considering that
          DELETE should be idempotent, we should return "NO_CONTENT" regardless the resource was
          found or not. However, we decided to inform the user that the resource was not found below.
        */
        if (!this.ordersService.exists(id, userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The entity was not found");
        }
        this.ordersService.delete(id, userId);
    }

    /**
     * Checkout order.
     *
     * @param userId Id of the owner.
     * @param id The id of the item to checkout.
     * @throws ResponseStatusException If the informed item was not found.
     */
    @Operation(description = "Checkout action.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "404", description = "NOT_FOUND")
    })
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = "/{id}/checkout", produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderDto checkout(
        @PathVariable("userId") Long userId,
        @Parameter(description = "Id of the resource to be modified.")
        @PathVariable("id") Long id
    ) throws ResponseStatusException {
        this.validateCustomer(userId);

        Order order = this.ordersService.getOne(id, userId).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The entity was not found")
        );

        return modelMapper.map(this.ordersService.completeOrder(order), OrderDto.class);
    }

    /**
     * Returns an user by its id.
     *
     * @param userId The id of the user.
     * @return The user with the requested id.
     * @throws ResponseStatusException If the user was not found.
     */
    private User getUserById(Long userId) throws ResponseStatusException {
        return this.usersService.getOne(userId).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The user was not found")
        );
    }

    /**
     * Converts an order DTO to an order object.
     *
     * @param dto The DTO to convert.
     * @return The order.
     */
    private Order convertOrderDtoToOrder(OrderSaveDto dto) {

        List<Long> productIds = dto.getItems().stream()
            .map(orderItemDto -> orderItemDto.getProductId())
            .collect(Collectors.toList());

        // TODO: Unnecessary database access and processing. We should removing it:
        List<Product> products = this.productsService.getAllIn(productIds);

        Map<Long, Product> productsMap = new HashMap<>();
        products.forEach(product -> productsMap.put(product.getId(), product));

        // TODO: use ModelMapper to perform this conversion
        Order orderToSave = new Order();

        List<OrderItem> items = dto.getItems().stream().map(orderItemDto -> {
            Long productId = orderItemDto.getProductId();

            // TODO: merge items with the same product
            if (productsMap.containsKey(productId)) {
                OrderItem item = new OrderItem();
                item.setOrder(orderToSave);
                item.setProductId(productId);
                item.setCount(orderItemDto.getCount());

                return item;
            }

            return null;
        }).filter(item -> item != null).collect(Collectors.toList());

        orderToSave.setItems(items);
        orderToSave.setStatus(OrderStatus.IN_PROGRESS);

        return orderToSave;
    }

    // TODO: The behavior is misplaced (an interceptor maybe?). We should refactor it when adding authorization support.
    /**
     * Validates whether the user is a customer.
     *
     * @param userId The id of the user.
     * @throws ResponseStatusException If the user was not found.
     */
    private void validateCustomer(Long userId) throws ResponseStatusException {
        User user = this.getUserById(userId);
        if (user instanceof Admin) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Admins cannot have orders");
        }
    }
}
