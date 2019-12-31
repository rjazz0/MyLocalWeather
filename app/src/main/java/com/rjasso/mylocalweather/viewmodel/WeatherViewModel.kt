package com.rjasso.mylocalweather.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rjasso.mylocalweather.model.RetrofitClient
import com.rjasso.mylocalweather.model.WeatherAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherViewModel: ViewModel() {
    private val weather: MutableLiveData<WeatherAPI> = MutableLiveData()
    fun getWeather(): LiveData<WeatherAPI> {
        return weather
    }
    fun loadWeather() {
        val map: HashMap<String,String> = HashMap()
        map.put("lat","37.421998333333335")
        map.put("lon","-122.08400000000002")
        map.put("appid","2ea768a867ac26f7b784a8e4191b356c")
        RetrofitClient.instance.weather(map).enqueue(object: Callback<WeatherAPI>{
            override fun onFailure(call: Call<WeatherAPI>, t: Throwable) {
                Log.d("Log!!","Error!! " + t.localizedMessage.toString())
            }

            override fun onResponse(call: Call<WeatherAPI>, response: Response<WeatherAPI>) {
                weather.value = response.body()
            }
        })
    }
}