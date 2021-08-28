package com.smarthardwareshop.api.users.controllers;

import com.smarthardwareshop.api.core.annotations.*;
import com.smarthardwareshop.api.users.dto.UserDto;
import com.smarthardwareshop.api.users.dto.UserSaveDto;
import com.smarthardwareshop.api.users.dto.UserUpdateDto;
import com.smarthardwareshop.api.users.entities.User;
import com.smarthardwareshop.api.users.services.UsersService;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

/**
 * Users REST controller.
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {

    /**
     * Model mapper used to transform entities from/to DTOs.
     */
    private final ModelMapper modelMapper;

    /**
     * The password encoder.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * The user service.
     */
    private final UsersService service;

    /**
     * Returns many items.
     *
     * @param filter A filter for the items.
     * @param pageable Pageable object build by Spring from the attributes page, size and sort.
     * @return A page of items.
     */
    @SwaggerGetMany
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<UserDto> getMany(@RequestParam(required = false) String filter, Pageable pageable) {
        Page<User> usersPage = this.service.getMany(filter, pageable);
        return usersPage.map(user -> modelMapper.map(user, UserDto.class));
    }

    /**
     * Returns a single item.
     *
     * @param id The ID of the item to return.
     * @return The requested item.
     * @throws EntityNotFoundException If the informed item was not found.
     */
    @SwaggerGetOne
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDto getOne(
        @ApiParam("Id of the resource to be obtained.")
        @PathVariable("id") Long id
    ) throws EntityNotFoundException {
        return modelMapper.map(
            this.service.getOne(id).orElseThrow(() -> new EntityNotFoundException()),
            UserDto.class
        );
    }

    /**
     * Saves an item.
     *
     * @param dto The DTO of the item to save.
     * @return The saved item.
     */
    @SwaggerSave
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDto save(
        @ApiParam("JSON representation of the resource to be saved.")
        @RequestBody @Valid UserSaveDto dto
    ) {
        dto.setPassword(this.passwordEncoder.encode(dto.getPassword()));
        User savedUser = this.service.save(modelMapper.map(dto, User.class));
        return modelMapper.map(savedUser, UserDto.class);
    }

    /**
     * Updates an item or creates a new item if the informed item does not exist.
     *
     * @param id The id of the item to update.
     * @param dto The DTO of the item to update.
     * @return The updated item.
     */
    @SwaggerUpdate
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> update(
        @ApiParam("Id of the resource to be updated or created.")
        @PathVariable("id") Long id,
        @ApiParam("JSON representation of the resource to be saved.")
        @RequestBody @Valid UserUpdateDto dto
    ) {
        HttpStatus resultStatus = HttpStatus.OK;

        dto.setPassword(this.passwordEncoder.encode(dto.getPassword()));
        User userToSave = modelMapper.map(dto, User.class);
        if (this.service.exists(id)) {
            userToSave.setId(id);
            resultStatus = HttpStatus.CREATED;
        }

        User savedUser = this.service.save(userToSave);
        return new ResponseEntity<>(modelMapper.map(savedUser, UserDto.class), resultStatus);
    }

    /**
     * Deletes an item.
     *
     * @param id The id of the item do delete.
     * @throws EntityNotFoundException If the informed item was not found.
     */
    @SwaggerDelete
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(
        @ApiParam("Id of the resource to be deleted.")
        @PathVariable("id") Long id
    ) throws EntityNotFoundException {
        /*
          The DELETE section of the RFC 7231 (https://tools.ietf.org/html/rfc7231#section-4.3.5)
          does not comment on what should be done if the resource was not found. Considering that
          DELETE should be idempotent, we should return "NO_CONTENT" regardless the resource was
          found or not. However, we decided to inform the user that the resource was not found below.
        */
        if (!this.service.exists(id)) {
            throw new EntityNotFoundException();
        }
        this.service.delete(id);
    }
}
