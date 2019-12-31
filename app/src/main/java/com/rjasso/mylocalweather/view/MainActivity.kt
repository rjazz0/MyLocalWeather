package com.rjasso.mylocalweather.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.rjasso.mylocalweather.R
import com.rjasso.mylocalweather.model.WeatherAPI
import com.rjasso.mylocalweather.viewmodel.WeatherViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layout: ConstraintLayout = constraintLayout

        val viewModel = WeatherViewModel()

        viewModel.getWeather().observe(this, Observer {
            weather: WeatherAPI -> Snackbar.make(layout, weather.name, Snackbar.LENGTH_LONG).show()
        })

        viewModel.loadWeather()
    }
}
