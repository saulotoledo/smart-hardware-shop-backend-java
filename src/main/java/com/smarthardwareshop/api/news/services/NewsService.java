package com.smarthardwareshop.api.news.services;

import com.smarthardwareshop.api.news.entities.News;
import com.smarthardwareshop.api.news.repositories.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * A service for news.
 */
@Service
@RequiredArgsConstructor
public class NewsService {

    /**
     * The news repository.
     */
    private final NewsRepository repository;

    /**
     * Returns many items. If a filter is informed, the results are filtered accordingly.
     *
     * @param filter The value used to filter the results.
     * @param pageable Object containing pagination information.
     * @return A page of news according to the informed specifications.
     */
    public Page<News> getMany(String filter, Pageable pageable) {
        if (filter != null) {
            return this.repository.findByTitleContaining(filter, pageable);
        }
        return this.repository.findAll(pageable);
    }

    /**
     * Returns a single item.
     *
     * @param id The ID of the item to return.
     * @return A single item.
     */
    public Optional<News> getOne(Long id) {
        return this.repository.findById(id);
    }

    /**
     * Saves an item.
     *
     * @param item The item to save.
     * @return The saved item.
     */
    public News save(News item) {
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
