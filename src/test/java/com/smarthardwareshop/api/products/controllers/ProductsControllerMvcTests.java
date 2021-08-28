package com.smarthardwareshop.api.products.controllers;

import com.smarthardwareshop.api.products.entities.Product;
import com.smarthardwareshop.api.products.services.ProductsService;
import com.smarthardwareshop.api.products.utils.ProductGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.*;
import org.springframework.data.domain.Sort.Order;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(controllers = ProductsController.class)
class ProductsControllerMvcTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductsService productsService;

    @TestConfiguration
    static class AdditionalConfig {
        @Bean
        public ModelMapper getModelMapper() {
            return new ModelMapper();
        }
    }

    @Test
    void successfullyAccessProductsList() throws Exception {
        List<Product> products = ProductGenerator.generateManyProducts(10);
        Pageable pageable = PageRequest.of(0, 2, Sort.by(Order.asc("name")));
        Page<Product> page = new PageImpl<>(products, pageable, products.size());

        when(productsService.getMany(any(), any())).thenReturn(page);

        mockMvc.perform(get("/products")
            .contentType("application/json"))
            .andExpect(status().isOk());
    }

    @Test
    void successfullyReceivePaginationArguments() throws Exception {
        List<Product> products = new ArrayList<>();
        Pageable pageable = PageRequest.of(5, 10, Sort.by(Order.asc("name")));
        Page<Product> page = new PageImpl<>(products, pageable, 10);

        when(productsService.getMany(any(), any())).thenReturn(page);

        mockMvc.perform(get("/products")
            .param("filter", "filter-value")
            .param("page", "5")
            .param("size", "10")
            .param("sort", "name,asc")
            .contentType("application/json"))
            .andExpect(status().isOk());

        ArgumentCaptor<String> filterCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(productsService).getMany(filterCaptor.capture(), pageableCaptor.capture());
        PageRequest capturedPageable = (PageRequest) pageableCaptor.getValue();

        assertEquals(filterCaptor.getValue(), "filter-value");
        assertEquals(capturedPageable.getPageNumber(), 4);
        assertEquals(capturedPageable.getPageSize(), 10);
        assertTrue(capturedPageable.getSort().toList().get(0).getDirection().equals(Sort.Direction.ASC));
        assertTrue(capturedPageable.getSort().toList().get(0).getProperty().equals("name"));
    }

    // TODO: additional tests (attributes validations, invalid inputs, post, put, delete, pagination etc.)
}
