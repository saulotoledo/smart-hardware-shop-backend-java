package com.smarthardwareshop.api.orders.repositories;

import com.smarthardwareshop.api.orders.entities.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for orders.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * Returns a page of orders.
     *
     * @param userId Id of the owner.
     * @param pageable Object containing pagination information.
     * @return A page of orders.
     */
    Page<Order> findAllByUserId(Long userId, Pageable pageable);

    /**
     * Returns a single order if it is owned by a given user.
     *
     * @param id Id of the order.
     * @param userId Id of the owner.
     * @return A page of orders.
     */
    Optional<Order> findByIdAndUserId(Long id, Long userId);

    /**
     * Returns whether an order with the informed id exists for a given user.
     *
     * @param id Id of the order.
     * @param userId Id of the owner.
     * @return true if the order exists, false otherwise.
     */
    boolean existsByIdAndUserId(Long id, Long userId);

    /**
     * Deletes the order with the informed id if it belongs to the informed user.
     *
     * @param id Id of the order.
     * @param userId Id of the owner.
     */
    void deleteByIdAndUserId(Long id, Long userId);
}
