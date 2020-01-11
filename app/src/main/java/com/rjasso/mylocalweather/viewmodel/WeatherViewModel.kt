package com.rjasso.mylocalweather.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rjasso.mylocalweather.model.Location
import com.rjasso.mylocalweather.model.Repository
import com.rjasso.mylocalweather.model.WeatherAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherViewModel(val repository: Repository): ViewModel() {

    private val weather: MutableLiveData<WeatherAPI> = MutableLiveData()
    private val location: MutableLiveData<Location> = MutableLiveData()

    init {
        location.value = Location()
    }

    fun getWeather(): LiveData<WeatherAPI> {
        return weather
    }

    fun loadWeather(latitude: String,longitude: String) {
        repository.getWeather(latitude,longitude).enqueue(object : Callback<WeatherAPI> {
            override fun onFailure(call: Call<WeatherAPI>, t: Throwable) {
                Log.d(Repository::javaClass.toString(), t.localizedMessage.toString())
            }

            override fun onResponse(call: Call<WeatherAPI>, response: Response<WeatherAPI>) {
                weather.value = response.body()

            }
        })

    }
}