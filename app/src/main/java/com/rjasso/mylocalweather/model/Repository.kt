package com.rjasso.mylocalweather.model

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.rjasso.mylocalweather.*
import com.rjasso.mylocalweather.viewmodel.RepositoryCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository {

    lateinit var db: WeatherDatabase.AppDatabase

    fun refreshWeather(lat: String,lon: String, callback: RepositoryCallback) {
        // Load latest weather data locally with room
        val weatherRoom = db.weatherDao().getWeather()

        weatherRoom?.let {
            callback.updateWeather(it)
        }

        // Load weather data from the API
        val map: HashMap<String, String> = HashMap()
        map.put(LAT, lat)
        map.put(LON, lon)
        map.put(APPID, WEATHER_APP_ID)
        RetrofitClient.instance.weather(map).enqueue(object: Callback<WeatherAPI>{
            override fun onFailure(call: Call<WeatherAPI>, t: Throwable) {
                Log.d(Repository::javaClass.toString(), t.localizedMessage.toString())
            }

            override fun onResponse(call: Call<WeatherAPI>, response: Response<WeatherAPI>) {
                val weatherRoom: WeatherRoom = Utils.getWeatherForRoom(response.body())
                callback.updateWeather(weatherRoom)
            }
        })
    }

    fun createDatabase(context: Context) {
        db = Room.databaseBuilder(
            context,
            WeatherDatabase.AppDatabase::class.java, "weather-database"
        ).build()
    }

}