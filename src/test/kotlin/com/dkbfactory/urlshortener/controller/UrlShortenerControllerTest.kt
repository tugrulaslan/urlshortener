package com.dkbfactory.urlshortener.controller

import com.dkbfactory.urlshortener.UrlshortenerApplication
import com.dkbfactory.urlshortener.controller.dto.LongUrlResponse
import com.dkbfactory.urlshortener.controller.dto.ShortUrlResponse
import com.dkbfactory.urlshortener.entity.UrlEntity
import com.dkbfactory.urlshortener.repository.UrlRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@SpringBootTest(classes = [UrlshortenerApplication::class])
@AutoConfigureMockMvc
class UrlShortenerControllerTest(
    @Autowired private val mockMvc: MockMvc,
    @Autowired private val mapper: ObjectMapper,
    @Autowired private val repository: UrlRepository
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
        val expectedShortUrlHash = "9e50ade"
        assertThat(shortUrlResponse.shortUrl).isEqualTo("tinyurl.com/$expectedShortUrlHash")
        val urlEntity = repository.findByUrlHash(expectedShortUrlHash)!!
        assertThat(urlEntity.urlHash).isEqualTo(expectedShortUrlHash)
        assertThat(urlEntity.longUrl).isEqualTo(longUrl)
    }

    @Test
    fun `should find no url data when it is not created earlier`() {
        //given
        val urlHash = "13v1cd"

        //when - then
        val response =
            mockMvc.get("/api/v1/urls/$urlHash") { contentType = MediaType.APPLICATION_JSON }
                .andExpect {
                    status { isNotFound() }
                }.andReturn()
    }

    @Test
    fun `should retrieve long url`() {
        //given
        val urlHash = "13cfdd6"
        val longUrl = "www.dkb.de/offers/black-friday"
        val mockUrlData = UrlEntity(urlHash, longUrl)
        repository.save(mockUrlData)

        //when
        val response =
            mockMvc.get("/api/v1/urls/$urlHash") { contentType = MediaType.APPLICATION_JSON }
                .andExpect {
                    status { isOk() }
                }.andReturn()

        //then
        val longUrlResponse =
            mapper.readValue(response.response.contentAsString, LongUrlResponse::class.java)
        assertThat(longUrlResponse.longUrl).isEqualTo(longUrl)
        val urlEntity = repository.findByUrlHash(urlHash)!!
        assertThat(urlEntity.urlHash).isEqualTo(urlHash)
        assertThat(urlEntity.longUrl).isEqualTo(longUrl)
        repository.delete(mockUrlData)
    }
}