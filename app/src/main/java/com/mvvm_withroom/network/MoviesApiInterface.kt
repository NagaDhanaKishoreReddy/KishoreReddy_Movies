package com.mvvm_withroom.network

import com.mvvm_withroom.response.MovieDetailsResponse
import com.mvvm_withroom.response.SearchMovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MoviesApiInterface {

    @GET("search/movie")
    suspend fun getMoviesList(
        @Query("api_key") apiKey: String?,
        @Query("language") language: String?,
        @Query("query") query: String?,
        @Query("page") page: Int?
    ): SearchMovieResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: String?,
        @Query("api_key") apiKey: String?,
        @Query("language") language: String?
    ): MovieDetailsResponse
}