package com.rjasso.mylocalweather.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface API {
    @GET("weather")
    fun weather(@QueryMap map: HashMap<String,String>): Call<WeatherAPI>
}