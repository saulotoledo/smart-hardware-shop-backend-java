package com.smarthardwareshop.api.users.entities;

import com.smarthardwareshop.api.users.enums.Role;

import javax.persistence.*;

/**
 * Describes an administrator.
 */
@Entity
@Table(name = "users")
@DiscriminatorValue(value = "ADMIN")
public class Admin extends User {

    /**
     * {@inheritDoc}
     */
    @Override
    public Role getRole() {
        return Role.ADMIN;
    }
}
