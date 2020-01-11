package com.rjasso.mylocalweather

object Utils {
    fun convertKelvinToFahrenheit(kelvin: Double): Double {
        return ((kelvin-273.15)*9/5+32)
    }
}