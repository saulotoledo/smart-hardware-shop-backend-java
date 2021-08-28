package com.smarthardwareshop.api.core.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * Generic traceable and identifiable entity. It contains the creation date and the last modified date
 * of the object. Both values are automatically updated by the AuditingEntityListener.
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class IdentifiableTraceableEntity extends IdentifiableEntity {

    /**
     * The object's creation date.
     */
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private @Getter @Setter LocalDateTime createdAt;

    /**
     * The object's last modified date.
     */
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private @Getter @Setter LocalDateTime updatedAt;
}
