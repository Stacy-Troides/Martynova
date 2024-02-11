package com.example.kinopoisk.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Films(
    val films: List<Film>,
    val pagesCount: Int
)