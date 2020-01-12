package com.rjasso.mylocalweather.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeatherDao {
    @Query("SELECT * FROM WeatherRoom WHERE id = 0")
    fun getWeather(): WeatherRoom

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(weather: WeatherRoom)
}