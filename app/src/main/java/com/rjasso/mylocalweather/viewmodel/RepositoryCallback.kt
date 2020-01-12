package com.rjasso.mylocalweather.viewmodel

import com.rjasso.mylocalweather.model.WeatherRoom

interface RepositoryCallback {
    fun updateWeather(weather: WeatherRoom)
}
