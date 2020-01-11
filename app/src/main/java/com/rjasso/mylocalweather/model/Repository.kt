package com.rjasso.mylocalweather.model

import com.rjasso.mylocalweather.WEATHER_APP_ID
import retrofit2.Call

class Repository {

    fun getWeather(lat: String,lon: String): Call<WeatherAPI> {
        val map: HashMap<String, String> = HashMap()
        map.put("lat", lat)
        map.put("lon", lon)
        map.put("appid", WEATHER_APP_ID)
        return RetrofitClient.instance.weather(map)

    }

}