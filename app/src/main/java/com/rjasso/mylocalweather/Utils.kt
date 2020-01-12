package com.rjasso.mylocalweather

import com.rjasso.mylocalweather.model.WeatherAPI
import com.rjasso.mylocalweather.model.WeatherRoom

object Utils {
    fun convertKelvinToFahrenheit(kelvin: Double?): Double {
        kelvin?.let { val result = ((kelvin-273.15)*9/5+32)
            var twoDigitsResult = (Math.round(result * 100.0) / 100.0)
            return twoDigitsResult }
        return 0.0
    }

    fun getWeatherForRoom(weather: WeatherAPI?): WeatherRoom {
        val weatherRoom = WeatherRoom(0,
            weather?.name ?: NO_NAME_AVAILABLE,
            weather?.main?.temp ?: NO_TEMP_AVAILABLE,
            weather?.weather?.let {
                it[FIRST_ELEMENT].icon ?: PLACEHOLDER_ICON
            },
            weather?.weather?.let {
                it[FIRST_ELEMENT].description ?: NO_DESCRIPTION_AVAILABLE
            }
        )
        return weatherRoom
    }
}