package com.mvvm_withroom.network

class NetworkHelper(private val apiService: MoviesApiInterface) {

    suspend fun getMoviesList() = apiService.getMoviesList("7b78f16f3621138129667e3f0c28850a", "en-US", "A", 1)
    suspend fun getMovieDetails() = apiService.getMovieDetails("379686", "7b78f16f3621138129667e3f0c28850a", "en-US", )
}