package com.mvvm_withroom.repository

import com.mvvm_withroom.network.NetworkHelper

class MoviesRepository(private val apiHelper: NetworkHelper) {

    suspend fun getMoviesList() = apiHelper.getMoviesList()
}