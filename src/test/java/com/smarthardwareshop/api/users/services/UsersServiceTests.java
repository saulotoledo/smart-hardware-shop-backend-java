package com.smarthardwareshop.api.users.services;

import com.smarthardwareshop.api.users.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

public class UsersServiceTests {

    @Autowired
    private UsersService usersService;

    @MockBean
    private UsersRepository usersRepository;

    // TODO: add service tests
}
