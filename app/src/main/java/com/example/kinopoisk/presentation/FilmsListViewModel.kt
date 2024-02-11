package com.example.kinopoisk.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinopoisk.data.model.Film
import com.example.kinopoisk.data.repository.KinopoiskRepositoryImpl
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class FilmsListViewModel : ViewModel() {
    private val listFilmsLiveData = MutableLiveData<List<Film>>()
    val listFilms: LiveData<List<Film>> = listFilmsLiveData
    private var job: Job? = null
    private val repository = KinopoiskRepositoryImpl()
    private val isLoadingLiveData = MutableLiveData<Boolean>(true)
    val isLoading: LiveData<Boolean> = isLoadingLiveData
    private val isErrorLiveData = MutableLiveData(false)
    val isError: LiveData<Boolean> = isErrorLiveData

    init {
        getFilmsList()
    }

    fun onRefreshList() {
        listFilmsLiveData.postValue(emptyList())
        job?.cancel()
        job = viewModelScope.launch {
            kotlin.runCatching {
                repository.getFilmsList()
            }.onSuccess {
                isLoadingLiveData.postValue(false)
                listFilmsLiveData.postValue(it)
                isErrorLiveData.value = false
            }.onFailure {
                isLoadingLiveData.postValue(false)
                isErrorLiveData.value = true
            }
        }
    }

    private fun getFilmsList() {
        job?.cancel()
        job = viewModelScope.launch {
            kotlin.runCatching {
                repository.getFilmsList()
            }.onSuccess {
                isLoadingLiveData.postValue(false)
                listFilmsLiveData.postValue(it)
                isErrorLiveData.value = false
            }.onFailure {
                isLoadingLiveData.postValue(false)
                isErrorLiveData.value = true
            }
        }
    }

    fun onSearchClicked(keyword: String) {
        job?.cancel()
        job = viewModelScope.launch {
            kotlin.runCatching {
                if (keyword.isBlank()) {
                    repository.getFilmsList()
                } else {
                    repository.getSearchByKeyword(keyword)
                }
            }.onSuccess {
                isLoadingLiveData.postValue(false)
                listFilmsLiveData.postValue(it)
                isErrorLiveData.value = false
            }.onFailure {
                isLoadingLiveData.postValue(false)
                isErrorLiveData.value = true
            }
        }
    }
}