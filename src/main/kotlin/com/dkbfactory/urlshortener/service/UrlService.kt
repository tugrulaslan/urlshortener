package com.dkbfactory.urlshortener.service

import com.dkbfactory.urlshortener.entity.UrlEntity
import com.dkbfactory.urlshortener.repository.UrlRepository
import org.apache.commons.codec.digest.DigestUtils
import org.springframework.stereotype.Service

@Service
class UrlService(private val urlRepository: UrlRepository) {
    fun shorten(longUrl: String): String {
        val shortUrlHash: String = DigestUtils.md5Hex(longUrl).substring(0, 7)
        val urlEntity = urlRepository.findByUrlHash(shortUrlHash)
        if (urlEntity == null) {
            urlRepository.save(UrlEntity(shortUrlHash, longUrl))
        }
        return "tinyurl.com/$shortUrlHash"
    }

    fun retrieveUrl(hash: String) = urlRepository.findByUrlHash(hash)
}