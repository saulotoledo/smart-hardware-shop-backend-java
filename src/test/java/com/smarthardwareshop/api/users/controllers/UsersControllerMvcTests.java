package com.smarthardwareshop.api.users.controllers;

import com.smarthardwareshop.api.users.enums.Role;
import com.smarthardwareshop.api.users.entities.User;
import com.smarthardwareshop.api.users.services.UsersService;
import com.smarthardwareshop.api.users.utils.UserGenerator;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UsersController.class)
// TODO: The line below disables security (among other things). We should try another approach.
@AutoConfigureMockMvc(addFilters = false)
class UsersControllerMvcTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsersService usersService;

    @TestConfiguration
    static class AdditionalConfig {
        @Bean
        public ModelMapper getModelMapper() {
            return new ModelMapper();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
    }

    @Test
    void successfullyAccessUsersList() throws Exception {
        List<User> users = UserGenerator.generateManyUsers(10, true, Role.ADMIN);
        Pageable pageable = PageRequest.of(0, 2, Sort.by(Order.asc("name")));
        Page<User> page = new PageImpl<>(users, pageable, users.size());

        when(usersService.getMany(any(), any())).thenReturn(page);

        mockMvc.perform(get("/users")
            .contentType("application/json"))
            .andExpect(status().isOk());
    }

    @Test
    void successfullyReceivePaginationArguments() throws Exception {
        List<User> users = new ArrayList<>();
        Pageable pageable = PageRequest.of(5, 10, Sort.by(Order.asc("name")));
        Page<User> page = new PageImpl<>(users, pageable, 10);

        when(usersService.getMany(any(), any())).thenReturn(page);

        mockMvc.perform(get("/users")
            .param("filter", "filter-value")
            .param("page", "5")
            .param("size", "10")
            .param("sort", "name,asc")
            .contentType("application/json"))
            .andExpect(status().isOk());

        ArgumentCaptor<String> filterCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(usersService).getMany(filterCaptor.capture(), pageableCaptor.capture());
        PageRequest capturedPageable = (PageRequest) pageableCaptor.getValue();

        assertEquals(filterCaptor.getValue(), "filter-value");
        assertEquals(capturedPageable.getPageNumber(), 4);
        assertEquals(capturedPageable.getPageSize(), 10);
        assertTrue(capturedPageable.getSort().toList().get(0).getDirection().equals(Sort.Direction.ASC));
        assertTrue(capturedPageable.getSort().toList().get(0).getProperty().equals("name"));
    }

    // TODO: additional tests (attributes validations, invalid inputs, post, put, delete, pagination, roles etc.)
}
