package com.smarthardwareshop.api.products.services;

import com.smarthardwareshop.api.products.entities.Product;
import com.smarthardwareshop.api.products.repositories.ProductsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * A service for products.
 */
@Service
@RequiredArgsConstructor
public class ProductsService {

    /**
     * The product repository.
     */
    private final ProductsRepository repository;

    /**
     * Returns many items. If a filter is informed, the results are filtered accordingly.
     *
     * @param filter The value used to filter the results.
     * @param pageable Object containing pagination information.
     * @return A page of products according to the informed specifications.
     */
    public Page<Product> getMany(String filter, Pageable pageable) {
        if (filter != null) {
            return this.repository.findByNameContaining(filter, pageable);
        }
        return this.repository.findAll(pageable);
    }

    /**
     * Returns all items with the informed IDs.
     *
     * @param ids List of IDs to return.
     * @return A page of products according to the informed specifications.
     */
    public List<Product> getAllIn(List<Long> ids) {
        return this.repository.findAllById(ids);
    }

    /**
     * Returns a single item.
     *
     * @param id The ID of the item to return.
     * @return A single item.
     */
    public Optional<Product> getOne(Long id) {
        return this.repository.findById(id);
    }

    /**
     * Saves an item.
     *
     * @param item The item to save.
     * @return The saved item.
     */
    public Product save(Product item) {
        return this.repository.save(item);
    }

    /**
     * Informs if an item with an informed ID exists in the database.
     *
     * @param id The ID of the item to search.
     * @return true if the item exists, false otherwise.
     */
    public boolean exists(Long id) {
        return this.repository.existsById(id);
    }

    /**
     * Deletes an item from the database.
     * @param id The ID of the item to delete.
     */
    public void delete(Long id) {
        this.repository.deleteById(id);
    }
}
