package com.mvvm_withroom.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MoviesBuilder {

    private const val BASE_URL = "https://api.themoviedb.org/3/"

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build() //Doesn't require the adapter
    }

    val apiService: MoviesApiInterface = getRetrofit().create(MoviesApiInterface::class.java)
}