package com.smarthardwareshop.api.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for model mapper used to convert entities from/to DTOs.
 */
@Configuration
public class ModelMapperConfiguration {

    /**
     * Returns model mapper instance.
     *
     * @return A model mapper instance.
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
