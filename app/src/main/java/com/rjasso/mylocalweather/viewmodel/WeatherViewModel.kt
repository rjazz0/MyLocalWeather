package com.rjasso.mylocalweather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rjasso.mylocalweather.model.WeatherAPI

class WeatherViewModel: ViewModel() {
    private val weather: MutableLiveData<WeatherAPI> = MutableLiveData()
    fun getWeather(): LiveData<WeatherAPI> {
        return weather
    }
    fun loadWeather() {

    }
}