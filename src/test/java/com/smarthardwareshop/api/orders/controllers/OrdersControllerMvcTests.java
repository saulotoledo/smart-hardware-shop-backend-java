package com.smarthardwareshop.api.orders.controllers;

import com.smarthardwareshop.api.products.controllers.ProductsController;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@WebMvcTest(controllers = ProductsController.class)
// TODO: The line below disables security (among other things). We should try another approach.
@AutoConfigureMockMvc(addFilters = false)
class OrdersControllerMvcTests {
    // TODO: test cases (attributes validations, invalid inputs, post, put, delete, pagination etc.)
}
