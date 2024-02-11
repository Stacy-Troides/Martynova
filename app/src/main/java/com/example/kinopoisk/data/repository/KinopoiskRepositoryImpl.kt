package com.example.kinopoisk.data.repository

import com.example.kinopoisk.data.remote.Networking
import com.example.kinopoisk.data.model.Film
import com.example.kinopoisk.data.model.FilmId
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class KinopoiskRepositoryImpl {

    suspend fun getFilmsList(): List<Film> =
        withContext(Dispatchers.IO){
        return@withContext Networking.cinemaSearchAPI.getFilmsList().films
    }

    suspend fun getFilm(id: Int): FilmId =
        withContext(Dispatchers.IO){
        return@withContext Networking.cinemaSearchAPI.getFilm(id)
    }

    suspend fun getSearchByKeyword(keyword: String): List<Film> =
        withContext(Dispatchers.IO){
        return@withContext  Networking.cinemaSearchAPI.getSearchByKeyword(keyword).films
    }
}