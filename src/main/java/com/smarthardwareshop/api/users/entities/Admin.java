package com.smarthardwareshop.api.users.entities;

import javax.persistence.*;

@Entity
@Table(name = "users")
@DiscriminatorValue(value = "ADMIN")
public class Admin extends User {
}
