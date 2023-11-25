package com.dkbfactory.urlshortener.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
data class UrlEntity(
    @Id
    @Column
    val urlHash: String,

    @Column
    val longUrl: String

)
