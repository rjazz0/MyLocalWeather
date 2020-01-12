package com.rjasso.mylocalweather.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WeatherRoom(
    @PrimaryKey val id: Int,
    val name: String?,
    val temp: Double?,
    val icon: String?,
    val description: String?
)