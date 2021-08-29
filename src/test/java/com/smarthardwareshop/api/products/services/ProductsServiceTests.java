package com.smarthardwareshop.api.products.services;

import com.smarthardwareshop.api.products.repositories.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

public class ProductsServiceTests {

    @Autowired
    private ProductsService productsService;

    @MockBean
    private ProductsRepository productsRepository;

    // TODO: add service tests
}
