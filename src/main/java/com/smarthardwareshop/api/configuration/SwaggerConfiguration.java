package com.smarthardwareshop.api.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDateTime;

/**
 * Swagger configuration.
 */
@Configuration
@EnableSwagger2
@RequiredArgsConstructor
public class SwaggerConfiguration {

    /**
     * OpenAPI information loaded from configuration files.
     */
    private final OpenApiInfo openAPIInfo;

    /**
     * Returns the main Swagger configuration.
     *
     * @return Swagger Docket instance.
     */
    @Bean
    public Docket appApi() {
        return new Docket(DocumentationType.OAS_30)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.smarthardwareshop.api"))
            .paths(PathSelectors.any())
            .build()
            .pathMapping("/")
            .apiInfo(this.apiInfo())
            .directModelSubstitute(LocalDateTime.class, String.class)
            .genericModelSubstitutes(ResponseEntity.class)
            .useDefaultResponseMessages(false)
            .enableUrlTemplating(false)
            .ignoredParameterTypes(Pageable.class);
    }

    /**
     * Build the basic API information.
     *
     * @return The builder containing the API information.
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title(this.openAPIInfo.getTitle())
            .description(this.openAPIInfo.getDescription())
            .license(this.openAPIInfo.getLicense())
            .licenseUrl(this.openAPIInfo.getLicenseUrl())
            .version(this.openAPIInfo.getVersion())
            .build();
    }

    /**
     * Swagger UI configuration.
     *
     * @return The Swagger UI configuration object.
     */
    @Bean
    UiConfiguration uiConfig() {
        return UiConfigurationBuilder.builder()
            .deepLinking(true)
            .displayOperationId(false)
            .defaultModelsExpandDepth(1)
            .defaultModelExpandDepth(1)
            .defaultModelRendering(ModelRendering.EXAMPLE)
            .displayRequestDuration(false)
            .docExpansion(DocExpansion.NONE)
            .filter(false)
            .maxDisplayedTags(null)
            .operationsSorter(OperationsSorter.ALPHA)
            .showExtensions(false)
            .showCommonExtensions(false)
            .tagsSorter(TagsSorter.ALPHA)
            .supportedSubmitMethods(UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS)
            .validatorUrl(null)
            .build();
    }
}
