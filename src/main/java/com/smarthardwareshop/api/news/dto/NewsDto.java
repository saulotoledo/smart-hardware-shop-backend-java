package com.smarthardwareshop.api.news.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * DTO for a returned news.
 */
@Getter
@Setter
public class NewsDto extends NewsUpdateDto {

    /**
     * The news's id.
     */
    @Schema(example = "1")
    private Long id;

    /**
     * The news's creation date.
     */
    @Schema(example = "2021-08-27T21:29:51.248Z")
    private LocalDateTime createdAt;

    /**
     * The news's last modified date.
     */
    @Schema(example = "2021-08-27T21:29:51.248Z")
    private LocalDateTime updatedAt;
}
