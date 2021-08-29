package com.smarthardwareshop.api.news.entities;

import com.github.javafaker.Faker;
import com.smarthardwareshop.api.news.utils.NewsGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class NewsTests {

    @Autowired
    private TestEntityManager entityManager;

    private News news;

    private final Faker faker = new Faker();

    @BeforeEach
    public void setUp() {
        news = NewsGenerator.generateNews();
    }

    @Test
    public void saveNewsInformation() {
        News newNews = NewsGenerator.generateNews();

        news.setTitle(newNews.getTitle());
        news.setDescription(newNews.getDescription());
        news.setExpiryDate(newNews.getExpiryDate());
        news.setImage(newNews.getImage());
        news.setCreatedAt(LocalDateTime.now());
        news.setUpdatedAt(LocalDateTime.now());

        News savedNews = this.entityManager.persistAndFlush(news);
        assertTrue(savedNews.getTitle().equals(newNews.getTitle()));
        assertTrue(savedNews.getDescription().equals(newNews.getDescription()));
        assertEquals(savedNews.getExpiryDate(), newNews.getExpiryDate());
        assertTrue(savedNews.getImage().equals(newNews.getImage()));
        assertNotNull(savedNews.getCreatedAt());
        assertNotNull(savedNews.getUpdatedAt());
        assertTrue(LocalDateTime.now().compareTo(savedNews.getCreatedAt()) > 0);
        assertTrue(LocalDateTime.now().compareTo(savedNews.getUpdatedAt()) > 0);
    }
}