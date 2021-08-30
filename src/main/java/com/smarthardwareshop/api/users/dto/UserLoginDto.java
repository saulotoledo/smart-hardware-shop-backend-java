package com.smarthardwareshop.api.users.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * DTO for a returned user.
 */
@Getter
@Setter
public class UserLoginDto {

    /**
     * The username of the user.
     */
    @Schema(example = "username")
    private String username;

    /**
     * The user's password.
     */
    @NotEmpty
    @Size(min = 8, message = "The password should have at least 8 characters")
    @Schema(example = "p@$$w0rd")
    private String password;
}
