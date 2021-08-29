package com.smarthardwareshop.api.news.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * DTO for a news that will be saved.
 */
@Getter
@Setter
public class NewsSaveDto {

    /**
     * The name of the news.
     */
    @NotEmpty
    @Size(min = 4, message = "The news title should have at least 4 characters")
    @ApiModelProperty(example = "New XPTO computer")
    private String title;

    /**
     * The description of the news.
     */
    @NotEmpty
    @Size(min = 20, message = "The news description should have at least 20 characters")
    @ApiModelProperty(example = "Lorem ipsum sit dolor amet")
    private String description;

    /**
     * The price of the news.
     */
    @NotNull
    @Future
    @ApiModelProperty(example = "2021-08-27T21:29:51.248Z")
    private LocalDateTime expiryDate;

    /**
     * The image of the news.
     */
    @ApiModelProperty(example = "http://www.some-fake-url.com/image.jpg")
    private String image;
}