package com.smarthardwareshop.api.news.repositories;

import com.smarthardwareshop.api.news.entities.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for products.
 */
@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    /**
     * Returns a page of products.
     *
     * @param pageable Object containing pagination information.
     * @return A page of products.
     */
    Page<News> findAll(Pageable pageable);

    /**
     * Returns a page of products according to a specific filter.
     *
     * @param filter The value used to filter the results.
     * @param pageable Object containing pagination information.
     * @return A page of products according to the informed filter.
     */
    Page<News> findByTitleContaining(String filter, Pageable pageable);
}
