package com.rjasso.mylocalweather.view

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import coil.api.load
import com.google.android.material.snackbar.Snackbar
import com.rjasso.mylocalweather.*
import com.rjasso.mylocalweather.model.Database
import com.rjasso.mylocalweather.model.Repository
import com.rjasso.mylocalweather.model.WeatherAPI
import com.rjasso.mylocalweather.model.WeatherRoom
import com.rjasso.mylocalweather.viewmodel.WeatherViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class MainActivity : AppCompatActivity() {
    val viewModel = WeatherViewModel(Repository())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.getWeather().observe(this, Observer {
            weather: WeatherRoom ->
            progressBar.visibility = View.GONE
            nameTextView.text = weather?.name
            degreesTextView.text = Utils.convertKelvinToFahrenheit(weather.temp).toString() + getString(R.string.fahrenheit)
            descriptionTextView.text = weather.description
            weatherImageView.load(getString(R.string.icon_url, weather.icon))
            GlobalScope.async {
                viewModel.repository.db.weatherDao().insert(weather)
            }
        })

        viewModel.repository.createDatabase(applicationContext)
        prepareLocationManager()
    }

    private fun prepareLocationManager() {
        try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                        checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager?
                        locationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, locationListener)
                    } else {
                        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), ACCESS_FINE_LOCATION_REQUEST)
                        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),ACCESS_COARSE_LOCATION_REQUEST)
                    }
                }
        } catch (ex: SecurityException) {
            Log.d(MainActivity::javaClass.toString(), ex.localizedMessage)
        }
    }

    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            GlobalScope.async {
                viewModel.loadWeather(location.latitude.toString(), location.longitude.toString())
            }
            progressBar.visibility = View.VISIBLE
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String?) {}

    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            ACCESS_COARSE_LOCATION_REQUEST -> {
                if ((grantResults.isNotEmpty() && grantResults[RESULT_POSITION] == PackageManager.PERMISSION_GRANTED)) {
                    prepareLocationManager()
                } else {
                    Snackbar.make(constraintLayout, getString(R.string.device_location_refuse_message),Snackbar.LENGTH_LONG).show()
                }
                return
            }
            ACCESS_FINE_LOCATION_REQUEST -> {
                if ((grantResults.isNotEmpty() && grantResults[RESULT_POSITION] == PackageManager.PERMISSION_GRANTED)) {
                    prepareLocationManager()
                } else {
                    Snackbar.make(constraintLayout,getString(R.string.device_location_refuse_message), Snackbar.LENGTH_LONG).show()
                }
                return
            }
        }
    }



}
