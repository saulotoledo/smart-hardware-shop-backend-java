package com.smarthardwareshop.api.products.entities;

import com.github.javafaker.Faker;
import com.smarthardwareshop.api.products.utils.ProductGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ProductTests {

    @Autowired
    private TestEntityManager entityManager;

    private Product product;

    private final Faker faker = new Faker();

    @BeforeEach
    public void setUp() {
        product = ProductGenerator.generateProduct();
    }

    @Test
    public void saveProductInformation() {
        Product newProduct = ProductGenerator.generateProduct();

        product.setName(newProduct.getName());
        product.setDescription(newProduct.getDescription());
        product.setPrice(newProduct.getPrice());
        product.setImage(newProduct.getImage());
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        Product savedProduct = this.entityManager.persistAndFlush(product);
        assertTrue(savedProduct.getName().equals(newProduct.getName()));
        assertTrue(savedProduct.getDescription().equals(newProduct.getDescription()));
        assertEquals(savedProduct.getPrice(), newProduct.getPrice());
        assertTrue(savedProduct.getImage().equals(newProduct.getImage()));
        assertNotNull(savedProduct.getCreatedAt());
        assertNotNull(savedProduct.getUpdatedAt());
        assertTrue(LocalDateTime.now().compareTo(savedProduct.getCreatedAt()) > 0);
        assertTrue(LocalDateTime.now().compareTo(savedProduct.getUpdatedAt()) > 0);
    }
}