package com.smarthardwareshop.api.users.entities;

import com.github.javafaker.Faker;
import com.smarthardwareshop.api.users.enums.Role;
import com.smarthardwareshop.api.users.utils.UserGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserTests {

    @Autowired
    private TestEntityManager entityManager;

    private User user;

    private final Faker faker = new Faker();

    @BeforeEach
    public void setUp() {
        user = UserGenerator.generateUser(true, Role.ADMIN);
    }

    @Test
    public void saveUserInformation() {
        User newUser = UserGenerator.generateUser(true, Role.ADMIN);

        user.setUsername(newUser.getUsername());
        user.setEnabled(newUser.isEnabled());
        user.setPassword(newUser.getPassword());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        User savedUser = this.entityManager.persistAndFlush(user);
        assertTrue(savedUser.getUsername().equals(newUser.getUsername()));
        assertTrue(savedUser.isEnabled() == newUser.isEnabled());
        assertEquals(savedUser.getPassword(), newUser.getPassword());
        assertNotNull(savedUser.getCreatedAt());
        assertNotNull(savedUser.getUpdatedAt());
        assertTrue(LocalDateTime.now().compareTo(savedUser.getCreatedAt()) > 0);
        assertTrue(LocalDateTime.now().compareTo(savedUser.getUpdatedAt()) > 0);
    }
}
