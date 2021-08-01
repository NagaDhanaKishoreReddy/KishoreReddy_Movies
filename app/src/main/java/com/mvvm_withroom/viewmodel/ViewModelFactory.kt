package com.mvvm_withroom.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mvvm_withroom.network.NetworkHelper
import com.mvvm_withroom.repository.MoviesRepository

class ViewModelFactory(private val networkHelper: NetworkHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MoviesViewModel::class.java)) {
            return MoviesViewModel(MoviesRepository(networkHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}