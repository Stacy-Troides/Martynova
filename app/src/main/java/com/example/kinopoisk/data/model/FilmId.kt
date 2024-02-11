package com.example.kinopoisk.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FilmId(
    val nameRu: String?,
    val countries: List<CountryX>,
    val description: String?,
    val genres: List<GenreX>,
    val posterUrl: String,
    val posterUrlPreview: String
)