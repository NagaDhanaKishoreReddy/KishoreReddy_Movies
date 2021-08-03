package com.mvvm_withroom.repository

import com.mvvm_withroom.network.NetworkHelper

class MoviesRepository(private val apiHelper: NetworkHelper) {

    suspend fun getMoviesList(apiKey: String, language: String, query: String, pageNo: Int) = apiHelper.getMoviesList(apiKey, language, query, pageNo)
    suspend fun getMovieDetails() = apiHelper.getMovieDetails()
}