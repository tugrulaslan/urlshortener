package com.dkbfactory.urlshortener.repository

import com.dkbfactory.urlshortener.entity.UrlEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UrlRepository : JpaRepository<UrlEntity, String> {
    fun findByUrlHash(shortUrl: String): UrlEntity?
}