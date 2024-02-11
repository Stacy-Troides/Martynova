package com.example.kinopoisk.data.remote

import com.example.kinopoisk.data.model.FilmId
import com.example.kinopoisk.data.model.Films
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface KinopoiskApi {

    @GET("/api/v2.2/films/top")
    @Headers("X-API-KEY: e30ffed0-76ab-4dd6-b41f-4c9da2b2735b")
    suspend fun getFilmsList(@Query("type") type: String = "TOP_100_POPULAR_FILMS"): Films

    @GET("/api/v2.2/films/{id}")
    @Headers("X-API-KEY: e30ffed0-76ab-4dd6-b41f-4c9da2b2735b")
    suspend fun getFilm(
        @Path("id") id: Int
    ): FilmId

    @GET("/api/v2.1/films/search-by-keyword")
    @Headers("X-API-KEY: e30ffed0-76ab-4dd6-b41f-4c9da2b2735b")
    suspend fun getSearchByKeyword(
        @Query("keyword") query: String
    ): Films
}