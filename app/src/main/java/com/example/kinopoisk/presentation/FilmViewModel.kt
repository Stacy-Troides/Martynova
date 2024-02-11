package com.example.kinopoisk.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinopoisk.data.repository.KinopoiskRepositoryImpl
import com.example.kinopoisk.domain.FilmItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FilmViewModel(
) : ViewModel() {
    private val repository = KinopoiskRepositoryImpl()

    private var _eventState = MutableStateFlow(FilmItem())
    val eventState = _eventState.asStateFlow()

    private val _isLoad = MutableStateFlow<Boolean>(true)
    val load = _isLoad

    fun initDetail(id: Int) {
        viewModelScope.launch {
            val eventItem = repository.getFilm(id)
            _eventState.update {
                _isLoad.value = false
                it.copy(
                    nameRu = eventItem.nameRu,
                    countries = eventItem.countries,
                    genres = eventItem.genres,
                    description = eventItem.description,
                    posterUrl = eventItem.posterUrl,
                    posterUrlPreview = eventItem.posterUrlPreview,
                )
            }
        }
    }
}