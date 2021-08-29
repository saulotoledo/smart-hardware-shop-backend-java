package com.smarthardwareshop.api.users.services;

import com.smarthardwareshop.api.core.validators.interfaces.FieldValueExists;
import com.smarthardwareshop.api.users.entities.User;
import com.smarthardwareshop.api.users.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * A service for users.
 */
@Service
@RequiredArgsConstructor
public class UsersService implements FieldValueExists {

    /**
     * The user repository.
     */
    private final UsersRepository repository;

    /**
     * Returns many items. If a filter is informed, the results are filtered accordingly.
     *
     * @param filter The value used to filter the results.
     * @param pageable Object containing pagination information.
     * @return A page of users according to the informed specifications.
     */
    public Page<User> getMany(String filter, Pageable pageable) {
        if (filter != null) {
            return this.repository.findByUsernameContaining(filter, pageable);
        }
        return this.repository.findAll(pageable);
    }

    /**
     * Returns a single item.
     *
     * @param id The ID of the item to return.
     * @return A single item.
     */
    public Optional<User> getOne(Long id) {
        return this.repository.findById(id);
    }

    /**
     * Saves an item.
     *
     * @param item The item to save.
     * @return The saved item.
     */
    public User save(User item) {
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

    /**
     * Returns whether a user exists with the informed username.
     *
     * @param value The value to check for.
     * @param fieldName The name of the field for which to check if the value exists.
     * @return true if the user exists, false otherwise.
     * @throws UnsupportedOperationException
     */
    @Override
    public boolean fieldValueExists(Object value, String fieldName) throws UnsupportedOperationException {
        if (!fieldName.equals("username")) {
            throw new UnsupportedOperationException("Field name not supported");
        }

        if (value == null) {
            return false;
        }

        return this.repository.existsByUsername(value.toString());
    }
}
