package com.smarthardwareshop.api.news.controllers;

import com.smarthardwareshop.api.core.annotations.*;
import com.smarthardwareshop.api.news.dto.NewsDto;
import com.smarthardwareshop.api.news.dto.NewsSaveDto;
import com.smarthardwareshop.api.news.dto.NewsUpdateDto;
import com.smarthardwareshop.api.news.entities.News;
import com.smarthardwareshop.api.news.services.NewsService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

/**
 * News REST controller.
 */
@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
@Tag(name = "News")
public class NewsController {

    /**
     * Model mapper used to transform entities from/to DTOs.
     */
    private final ModelMapper modelMapper;

    /**
     * The news service.
     */
    private final NewsService service;

    /**
     * Returns many items.
     *
     * @param filter A filter for the items.
     * @param pageable Pageable object build by Spring from the attributes page, size and sort.
     * @return A page of items.
     */
    @SwaggerGetMany
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<NewsDto> getMany(@RequestParam(required = false) String filter, Pageable pageable) {
        Page<News> newsPage = this.service.getMany(filter, pageable);
        return newsPage.map(news -> modelMapper.map(news, NewsDto.class));
    }

    /**
     * Returns a single item.
     *
     * @param id The ID of the item to return.
     * @return The requested item.
     * @throws ResponseStatusException If the informed item was not found.
     */
    @SwaggerGetOne
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public NewsDto getOne(
        @Parameter(description = "Id of the resource to be obtained.")
        @PathVariable("id") Long id
    ) throws ResponseStatusException {
        return modelMapper.map(
            this.service.getOne(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The entity was not found")
            ),
            NewsDto.class
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
    public NewsDto save(
        @Parameter(description = "JSON representation of the resource to be saved.")
        @RequestBody @Valid NewsSaveDto dto
    ) {
        News savedNews = this.service.save(modelMapper.map(dto, News.class));
        return modelMapper.map(savedNews, NewsDto.class);
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
    public ResponseEntity<NewsDto> update(
        @Parameter(description = "Id of the resource to be updated or created.")
        @PathVariable("id") Long id,
        @Parameter(description = "JSON representation of the resource to be saved.")
        @RequestBody @Valid NewsUpdateDto dto
    ) {
        HttpStatus resultStatus = HttpStatus.OK;

        News newsToSave = modelMapper.map(dto, News.class);
        if (this.service.exists(id)) {
            newsToSave.setId(id);
            resultStatus = HttpStatus.CREATED;
        }

        News savedNews = this.service.save(newsToSave);
        return new ResponseEntity<>(modelMapper.map(savedNews, NewsDto.class), resultStatus);
    }

    /**
     * Deletes an item.
     *
     * @param id The id of the item do delete.
     * @throws ResponseStatusException If the informed item was not found.
     */
    @SwaggerDelete
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(
        @Parameter(description = "Id of the resource to be deleted.")
        @PathVariable("id") Long id
    ) throws ResponseStatusException {
        /*
          The DELETE section of the RFC 7231 (https://tools.ietf.org/html/rfc7231#section-4.3.5)
          does not comment on what should be done if the resource was not found. Considering that
          DELETE should be idempotent, we should return "NO_CONTENT" regardless the resource was
          found or not. However, we decided to inform the user that the resource was not found below.
        */
        if (!this.service.exists(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The entity was not found");
        }
        this.service.delete(id);
    }
}
