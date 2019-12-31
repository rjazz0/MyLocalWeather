package com.rjasso.mylocalweather.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    const val BASEURL = "https://api.openweathermap.org/data/2.5/"
    val instance by lazy {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASEURL)
            .build()

        retrofit.create(API::class.java)
    }
}