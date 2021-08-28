package com.smarthardwareshop.api.users.repositories;

import com.smarthardwareshop.api.users.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for users.
 */
@Repository
public interface UsersRepository extends JpaRepository<User, Long> {

    /**
     * Returns a page of users.
     *
     * @param pageable Object containing pagination information.
     * @return A page of users.
     */
    Page<User> findAll(Pageable pageable);

    /**
     * Returns a page of users according to a specific filter.
     *
     * @param username The username of the user.
     * @return A page of users according to the informed filter.
     */
    User findByUsername(String username);

    /**
     * Returns a page of users according to a specific filter.
     *
     * @param filter The value used to filter the results.
     * @param pageable Object containing pagination information.
     * @return A page of users according to the informed filter.
     */
    Page<User> findByUsernameContaining(String filter, Pageable pageable);

    /**
     * Returns whether a user exists with the informed username.
     *
     * @param username The username of the user.
     * @return true if the user exists, false otherwise.
     */
    boolean existsByUsername(String username);
}
