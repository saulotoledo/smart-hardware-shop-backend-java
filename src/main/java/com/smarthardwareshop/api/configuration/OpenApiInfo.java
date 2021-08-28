package com.smarthardwareshop.api.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI information loaded from configuration files.
 */
@Configuration
@Getter
@Setter
@ConfigurationProperties("openapi")
class OpenApiInfo {

    /**
     * The title of the API.
     */
    private String title;

    /**
     * The description of the API.
     */
    private String description;

    /**
     * The license of the API.
     */
    private String license;

    /**
     * The URL of the API license.
     */
    private String licenseUrl;

    /**
     * The version of the API.
     */
    private String version;
}
