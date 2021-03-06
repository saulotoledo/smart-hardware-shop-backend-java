package com.smarthardwareshop.api.core.annotations;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Configures a default save endpoint in Swagger.
 */
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Operation(description = "Saves an item.", security = @SecurityRequirement(name = "bearerAuth"))
@ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Created")
})
@ResponseStatus(value = HttpStatus.CREATED)
public @interface SwaggerSave {
}
