package com.smarthardwareshop.api.users.dto;

import com.smarthardwareshop.api.users.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(example = "1")
    private Long id;

    /**
     * The username of the user.
     */
    @Schema(example = "username")
    private String username;

    /**
     * Informs if the user is enabled.
     */
    @Schema(example = "true")
    private Boolean enabled;

    /**
     * The role of the user.
     */
    @Schema(example = "ADMIN")
    private Role role;

    /**
     * The user's creation date.
     */
    @Schema(example = "2021-08-27T21:29:51.248Z")
    private LocalDateTime createdAt;

    /**
     * The user's last modified date.
     */
    @Schema(example = "2021-08-27T21:29:51.248Z")
    private LocalDateTime updatedAt;
}
