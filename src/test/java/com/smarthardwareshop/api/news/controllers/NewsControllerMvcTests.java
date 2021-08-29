package com.smarthardwareshop.api.news.controllers;

import com.smarthardwareshop.api.news.entities.News;
import com.smarthardwareshop.api.news.services.NewsService;
import com.smarthardwareshop.api.news.utils.NewsGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.*;
import org.springframework.data.domain.Sort.Order;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = NewsController.class)
// TODO: The line below disables security (among other things). We should try another approach.
@AutoConfigureMockMvc(addFilters = false)
class NewsControllerMvcTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NewsService newsService;

    @TestConfiguration
    static class AdditionalConfig {
        @Bean
        public ModelMapper getModelMapper() {
            return new ModelMapper();
        }
    }

    @Test
    void successfullyAccessNewsList() throws Exception {
        List<News> news = NewsGenerator.generateManyNews(10);
        Pageable pageable = PageRequest.of(0, 2, Sort.by(Order.asc("name")));
        Page<News> page = new PageImpl<>(news, pageable, news.size());

        when(newsService.getMany(any(), any())).thenReturn(page);

        mockMvc.perform(get("/news")
            .contentType("application/json"))
            .andExpect(status().isOk());
    }

    @Test
    void successfullyReceivePaginationArguments() throws Exception {
        List<News> news = new ArrayList<>();
        Pageable pageable = PageRequest.of(5, 10, Sort.by(Order.asc("name")));
        Page<News> page = new PageImpl<>(news, pageable, 10);

        when(newsService.getMany(any(), any())).thenReturn(page);

        mockMvc.perform(get("/news")
            .param("filter", "filter-value")
            .param("page", "5")
            .param("size", "10")
            .param("sort", "name,asc")
            .contentType("application/json"))
            .andExpect(status().isOk());

        ArgumentCaptor<String> filterCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(newsService).getMany(filterCaptor.capture(), pageableCaptor.capture());
        PageRequest capturedPageable = (PageRequest) pageableCaptor.getValue();

        assertEquals(filterCaptor.getValue(), "filter-value");
        assertEquals(capturedPageable.getPageNumber(), 4);
        assertEquals(capturedPageable.getPageSize(), 10);
        assertTrue(capturedPageable.getSort().toList().get(0).getDirection().equals(Sort.Direction.ASC));
        assertTrue(capturedPageable.getSort().toList().get(0).getProperty().equals("name"));
    }

    // TODO: additional tests (attributes validations, invalid inputs, post, put, delete, pagination etc.)
}
