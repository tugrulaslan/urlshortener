package com.dkbfactory.urlshortener.service

import com.dkbfactory.urlshortener.entity.UrlEntity
import com.dkbfactory.urlshortener.repository.UrlRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.slot
import org.apache.commons.codec.digest.DigestUtils
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class UrlServiceTest {

    private val repository: UrlRepository = mockk<UrlRepository>(relaxUnitFun = true)
    private val service: UrlService = UrlService(repository)

    @Test
    fun `should shorten long url`() {
        //given
        mockkStatic(DigestUtils::class)
        val longUrl = "www.dkb.de/offers/black-friday"
        val urlSlot = slot<String>()
        val persistedEntitySlot = slot<UrlEntity>()
        val shortUrlMd5 = "abc12356"
        val shortUrl = "abc12356".substring(0, 7)
        every { DigestUtils.md5Hex(capture(urlSlot)) } returns shortUrlMd5
        every { repository.findByUrlHash(eq(shortUrl)) } returns null
        every { repository.save(capture(persistedEntitySlot)) } returns mockk<UrlEntity>()

        //when
        val shortenUrl = service.shorten(longUrl)

        //then
        assertThat(shortenUrl).isEqualTo("tinyurl.com/$shortUrl")
        assertThat(urlSlot.captured).isEqualTo(longUrl)
        assertThat(persistedEntitySlot.captured.urlHash).isEqualTo(shortUrl)
        assertThat(persistedEntitySlot.captured.longUrl).isEqualTo(longUrl)
    }

    @Test
    fun `should find url by url hash`() {
        //given
        val urlHash = "abc1235"
        val longUrl = "www.dkb.de/offers/black-friday"
        every { repository.findByUrlHash(eq(urlHash)) } returns UrlEntity(urlHash, longUrl)

        //when
        val urlEntity = service.retrieveUrl(urlHash)!!

        //then
        assertThat(urlEntity.urlHash).isEqualTo(urlHash)
        assertThat(urlEntity.longUrl).isEqualTo(longUrl)
    }
}