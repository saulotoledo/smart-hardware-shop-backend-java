package com.smarthardwareshop.api.core.annotations;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Configures a default delete endpoint in Swagger.
 */
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ApiOperation("Removes a item.")
@ApiResponses(value = {
    @ApiResponse(responseCode = "204", description = "No Content"),
    @ApiResponse(
        responseCode = "404",
        description = "Not Found"
    )
})
@ResponseStatus(value = HttpStatus.NO_CONTENT)
public @interface SwaggerDelete {
}
