package com.smarthardwareshop.api.news.services;

import com.smarthardwareshop.api.products.repositories.ProductsRepository;
import com.smarthardwareshop.api.products.services.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

public class NewsServiceTests {

    @Autowired
    private ProductsService productsService;

    @MockBean
    private ProductsRepository productsRepository;

    // TODO: add service tests
}