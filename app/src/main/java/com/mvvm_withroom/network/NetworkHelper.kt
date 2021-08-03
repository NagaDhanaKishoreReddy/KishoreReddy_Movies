package com.mvvm_withroom.network

class NetworkHelper(private val apiService: MoviesApiInterface) {

    suspend fun getMoviesList(apiKey: String, language: String, query: String, pageNo: Int) = apiService.getMoviesList(apiKey, language, query, pageNo)
    suspend fun getMovieDetails(movieId: String?, apiKey: String, language: String) = apiService.getMovieDetails(movieId,apiKey, language)
}