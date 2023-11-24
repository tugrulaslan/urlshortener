package com.dkbfactory.urlshortener.controller

import com.dkbfactory.urlshortener.UrlshortenerApplication
import com.dkbfactory.urlshortener.controller.dto.ShortUrlResponse
import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@SpringBootTest(classes = [UrlshortenerApplication::class])
@AutoConfigureMockMvc
class UrlShortenerControllerTest(
    @Autowired private val mockMvc: MockMvc,
    @Autowired private val mapper: ObjectMapper
) {

    @Test
    fun `should create short url`() {
        //given
        val longUrl = "www.dkb.de"

        //when
        val response =
            mockMvc.post("/api/v1/urls/$longUrl") { contentType = MediaType.APPLICATION_JSON }
                .andExpect {
                    status { isCreated() }
                }.andReturn()

        //then
        val shortUrlResponse =
            mapper.readValue(response.response.contentAsString, ShortUrlResponse::class.java)
        assertThat(shortUrlResponse.shortUrl).isEqualTo("tinyurl.com/abc123")
    }
}