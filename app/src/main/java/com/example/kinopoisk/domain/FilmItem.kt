package com.example.kinopoisk.domain

import com.example.kinopoisk.data.model.CountryX
import com.example.kinopoisk.data.model.GenreX

data class FilmItem(
    val countries: List<CountryX> = emptyList(),
    val genres: List<GenreX> = emptyList(),
    val description: String? = String(),
    val nameRu: String? = String(),
    val posterUrl: String = String(),
    val posterUrlPreview: String = String(),
)
