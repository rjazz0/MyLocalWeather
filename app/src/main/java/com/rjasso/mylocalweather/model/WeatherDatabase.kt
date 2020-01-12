package com.rjasso.mylocalweather.model

import androidx.room.Database
import androidx.room.RoomDatabase

class WeatherDatabase {

    @Database(entities = arrayOf(WeatherRoom::class), version = 1)
    abstract class AppDatabase : RoomDatabase() {
        abstract fun weatherDao(): WeatherDao
    }
}