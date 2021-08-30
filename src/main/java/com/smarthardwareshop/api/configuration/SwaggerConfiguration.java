package com.smarthardwareshop.api.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.SpringDocUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

/**
 * Swagger configuration.
 */
@Configuration
@RequiredArgsConstructor
public class SwaggerConfiguration {

    /**
     * OpenAPI information loaded from configuration files.
     */
    private final OpenApiInfo openAPIInfo;

    static {
        SpringDocUtils.getConfig()
            .replaceWithClass(LocalDateTime.class, String.class);
    }

    /**
     * Returns the main Swagger configuration.
     *
     * @return Swagger Docket instance.
     */
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
            .group("springshop-public")
            .pathsToMatch("/**")
            .build();
    }

    /**
     * Builds the basic API information.
     *
     * @return The basic API information.
     */
    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
            .info(new Info().title(this.openAPIInfo.getTitle())
                .description(this.openAPIInfo.getDescription())
                .version(this.openAPIInfo.getVersion())
                .license(new License().name(this.openAPIInfo.getLicense()).url(this.openAPIInfo.getLicenseUrl())));
    }
}
