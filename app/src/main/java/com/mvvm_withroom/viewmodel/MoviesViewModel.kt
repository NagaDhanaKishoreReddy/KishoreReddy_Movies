package com.mvvm_withroom.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.mvvm_withroom.repository.MoviesRepository
import com.mvvm_withroom.utils.Resource
import kotlinx.coroutines.Dispatchers

class MoviesViewModel(private val moviesRepository: MoviesRepository) : ViewModel() {

    fun getMoviesList(apiKey: String, language: String, query: String, pageNo: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = moviesRepository.getMoviesList(apiKey, language, query, pageNo)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getMovieDetails() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = moviesRepository.getMovieDetails()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}