package com.smarthardwareshop.api.products.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.smarthardwareshop.api.products.entities.Product;

/**
 * Repository for products.
 */
@Repository
public interface ProductsRepository extends JpaRepository<Product, Long> {

    /**
     * Returns a page of products.
     *
     * @param pageable Object containing pagination information.
     * @return A page of products.
     */
    Page<Product> findAll(Pageable pageable);

    /**
     * Returns a page of products according to a specific filter.
     *
     * @param filter The value used to filter the results.
     * @param pageable Object containing pagination information.
     * @return A page of products according to the informed filter.
     */
    Page<Product> findByNameContaining(String filter, Pageable pageable);
}
