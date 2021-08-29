package com.smarthardwareshop.api.orders.services;

import com.smarthardwareshop.api.orders.entities.Order;
import com.smarthardwareshop.api.orders.enums.OrderStatus;
import com.smarthardwareshop.api.orders.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * A service for orders.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class OrdersService {

    /**
     * The orders repository.
     */
    private final OrderRepository repository;

    /**
     * Returns many items for a given user. If a filter is informed, the results are filtered accordingly.
     *
     * @param userId Id of the owner.
     * @param pageable Object containing pagination information.
     * @return A page of orders according to the informed specifications.
     */
    public Page<Order> getMany(Long userId, Pageable pageable) {
        return this.repository.findAllByUserId(userId, pageable);
    }

    /**
     * Returns a single item if it belongs to the informed user.
     *
     * @param id The ID of the item to return.
     * @param userId Id of the owner.
     * @return A single item.
     */
    public Optional<Order> getOne(Long id, Long userId) {
        return this.repository.findByIdAndUserId(id, userId);
    }

    /**
     * Saves an item.
     *
     * @param item The item to save.
     * @return The saved item.
     */
    public Order save(Order item) {
        return this.repository.save(item);
    }

    /**
     * Informs if an item with an informed ID exists in the database for the informed user.
     *
     * @param id The ID of the item to search.
     * @param userId Id of the owner.
     * @return true if the item exists, false otherwise.
     */
    public boolean exists(Long id, Long userId) {
        return this.repository.existsByIdAndUserId(id, userId);
    }

    /**
     * Deletes an item from the database if it belongs to the informed user.
     *
     * @param id The ID of the item to delete.
     * @param userId Id of the owner.
     */
    public void delete(Long id, Long userId) {
        this.repository.deleteByIdAndUserId(id, userId);
    }

    /**
     * Completes an order.
     *
     * @param item The order to complete.
     */
    public Order completeOrder(Order item) {
        item.setStatus(OrderStatus.COMPLETED);
        return this.save(item);
    }
}
