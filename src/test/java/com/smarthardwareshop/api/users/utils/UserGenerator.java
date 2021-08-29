package com.smarthardwareshop.api.users.utils;

import com.github.javafaker.Faker;
import com.smarthardwareshop.api.users.entities.Admin;
import com.smarthardwareshop.api.users.entities.Customer;
import com.smarthardwareshop.api.users.enums.Role;
import com.smarthardwareshop.api.users.entities.User;

import java.util.ArrayList;
import java.util.List;

public class UserGenerator {
    private static final Faker faker = new Faker();

    public static User generateUser(boolean enabled, Role role) {
        User user = new Customer();
        if (role.equals(Role.ADMIN)) {
            user = new Admin();
        }

        user.setUsername(faker.name().username());
        user.setEnabled(enabled);
        user.setPassword(faker.internet().password());

        return user;
    }

    public static List<User> generateManyUsers(int quantity, boolean enabled, Role role) {
        List<User> users = new ArrayList<>();

        for (int i = 0; i < quantity; i++) {
            User user = UserGenerator.generateUser(enabled, role);
            users.add(user);
        }

        return users;
    }
}
