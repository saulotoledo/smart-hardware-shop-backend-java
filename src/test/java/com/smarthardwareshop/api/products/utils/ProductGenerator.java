package com.smarthardwareshop.api.products.utils;

import com.github.javafaker.Faker;
import com.smarthardwareshop.api.products.entities.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductGenerator {
    private static final Faker faker = new Faker();

    public static Product generateProduct() {
        Product product = new Product();
        product.setName(faker.commerce().productName());
        product.setDescription(faker.lorem().sentence());
        product.setPrice(faker.number().randomDouble(2, 1, 10000));
        product.setImage(faker.internet().url());

        return product;
    }

    public static List<Product> generateManyProducts(int quantity) {
        List<Product> products = new ArrayList<>();

        for (int i = 0; i < quantity; i++) {
            Product product = ProductGenerator.generateProduct();
            products.add(product);
        }

        return products;
    }
}
