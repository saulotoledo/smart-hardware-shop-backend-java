package com.smarthardwareshop.api.users.dto;

import com.smarthardwareshop.api.core.validators.annotations.Unique;
import com.smarthardwareshop.api.users.enums.Role;
import com.smarthardwareshop.api.users.services.UsersService;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * DTO for a user that will be saved.
 */
@Getter
@Setter
public class UserSaveDto {

    /**
     * The username of the user.
     */
    @NotEmpty
    @Size(min = 4, max = 50, message = "The username should have from 4 to 50 characters")
    @Pattern(regexp = "[a-zA-Z0-9-]+", message = "The username should only contain letters, numbers and '-'")
    @Unique(service = UsersService.class, fieldName = "username", message = "The informed username already exists")
    @ApiModelProperty(example = "username")
    private String username;

    /**
     * Informs if the user is enabled.
     */
    @NotNull
    @ApiModelProperty(example = "true")
    private Boolean enabled;

    /**
     * The role of the user.
     */
    @ApiModelProperty(example = "ADMIN")
    private Role role;

    /**
     * The user's password.
     */
    @NotEmpty
    @Size(min = 8, message = "The password should have at least 8 characters")
    @ApiModelProperty(example = "p@$$w0rd")
    private String password;
}
