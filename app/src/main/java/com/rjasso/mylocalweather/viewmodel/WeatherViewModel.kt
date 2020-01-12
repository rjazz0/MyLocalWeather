package com.rjasso.mylocalweather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rjasso.mylocalweather.model.Repository
import com.rjasso.mylocalweather.model.WeatherRoom

class WeatherViewModel(val repository: Repository): ViewModel(), RepositoryCallback {

    private val weather: MutableLiveData<WeatherRoom> = MutableLiveData()

    fun getWeather(): LiveData<WeatherRoom> {
        return weather
    }

    fun loadWeather(latitude: String,longitude: String) {
        repository.refreshWeather(latitude,longitude, this)
    }

    override fun updateWeather(updatedWeather: WeatherRoom) {
        weather.postValue(updatedWeather)
    }
}