package com.example.kinopoisk.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CountryX(
    val country: String
)