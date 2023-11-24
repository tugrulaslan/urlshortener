package com.dkbfactory.urlshortener.controller

import com.dkbfactory.urlshortener.controller.dto.ShortUrlResponse
import jakarta.validation.constraints.NotBlank
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/urls")
@Validated
class UrlShortenerController {

    @PostMapping(
        "/{url}", consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun createUrl(@PathVariable("url") @NotBlank url: String): ResponseEntity<ShortUrlResponse> {
        return ResponseEntity(ShortUrlResponse("tinyurl.com/abc123"), HttpStatus.CREATED)
    }
}