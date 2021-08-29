package com.smarthardwareshop.api.news.entities;

import com.smarthardwareshop.api.core.entities.IdentifiableTraceableEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * Describes a news.
 */
@Entity
@Table(name = "news")
public @Data class News extends IdentifiableTraceableEntity {

    /**
     * The name of the news.
     */
    @Column(name = "title", length = 150, nullable = false)
    private String title;

    /**
     * The description of the news.
     */
    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    private String description;

    /**
     * The price of the news.
     */
    @Column(name = "expiryDate", nullable = false)
    private LocalDateTime expiryDate;

    /**
     * The image of the news.
     */
    @Column(name = "image")
    private String image;
}
