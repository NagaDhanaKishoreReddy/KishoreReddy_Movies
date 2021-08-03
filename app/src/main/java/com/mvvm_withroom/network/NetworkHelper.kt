package com.mvvm_withroom.network

class NetworkHelper(private val apiService: MoviesApiInterface) {

    suspend fun getMoviesList(apiKey: String, language: String, query: String, pageNo: Int) = apiService.getMoviesList(apiKey, language, query, pageNo)
    suspend fun getMovieDetails() = apiService.getMovieDetails("379686", "7b78f16f3621138129667e3f0c28850a", "en-US", )
}