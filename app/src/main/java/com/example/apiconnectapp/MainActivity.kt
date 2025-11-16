package com.example.apiconnectapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.apiconnectapp.instance.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun getWeather(city: String, apiKey: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Get Coordinates
                val geo = RetrofitClient.service.getCoordinates(city, 1, apiKey)

                if(geo.isNotEmpty()) {
                    val lat = geo[0].lat
                    val lon = geo[0].lon
                    val country = geo[0].country

                    val weather = RetrofitClient.service.getWeather(lat, lon, apiKey)

                    withContext(Dispatchers.Main) {
                        println("City: ${weather.name}")
                        println("Country: $country")
                        println("Temperature: ${weather.main.temp}°C")
                        println("Feels like: ${weather.main.feels_like}°C")
                        println("Humidity: ${weather.main.humidity}%")
                        println("Description: ${weather.weather[0].description}")
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}