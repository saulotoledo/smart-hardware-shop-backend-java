package com.smarthardwareshop.api.users.dto;

import com.smarthardwareshop.api.users.enums.Role;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * DTO for a returned user.
 */
@Getter
@Setter
public class UserDto {

    /**
     * The user id.
     */
    @ApiModelProperty(example = "1")
    private Long id;

    /**
     * The username of the user.
     */
    @ApiModelProperty(example = "username")
    private String username;

    /**
     * Informs if the user is enabled.
     */
    @ApiModelProperty(example = "true")
    private Boolean enabled;

    /**
     * The role of the user.
     */
    @ApiModelProperty(example = "ADMIN")
    private Role role;

    /**
     * The user's creation date.
     */
    @ApiModelProperty(example = "2021-08-27T21:29:51.248Z")
    private LocalDateTime createdAt;

    /**
     * The user's last modified date.
     */
    @ApiModelProperty(example = "2021-08-27T21:29:51.248Z")
    private LocalDateTime updatedAt;
}
