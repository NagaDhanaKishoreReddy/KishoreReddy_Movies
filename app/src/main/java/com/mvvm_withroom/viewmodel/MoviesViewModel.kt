package com.mvvm_withroom.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.mvvm_withroom.repository.MoviesRepository
import com.mvvm_withroom.utils.Resource
import kotlinx.coroutines.Dispatchers

class MoviesViewModel(private val moviesRepository: MoviesRepository) : ViewModel() {

    fun getMoviesList() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = moviesRepository.getMoviesList()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}