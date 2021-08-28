package com.smarthardwareshop.api.users.entities;

import com.smarthardwareshop.api.core.entities.IdentifiableTraceableEntity;
import com.smarthardwareshop.api.users.enums.Role;
import lombok.Data;

import javax.persistence.*;

/**
 * The user entity.
 */
@Entity
@Table(name = "users")
public @Data class User extends IdentifiableTraceableEntity {

    /**
     * The user's username.
     */
    @Column(name = "username", length = 50, nullable = false, unique = true)
    private String username;

    /**
     * The user's password.
     */
    @Column(name = "password", length = 60, nullable = false)
    private String password;

    /**
     * Informs if the user is enabled.
     */
    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    /**
     * The user's role.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;
}
