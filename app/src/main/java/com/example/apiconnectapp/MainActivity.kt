package com.example.apiconnectapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.apiconnectapp.instance.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

        val fetchBtn = findViewById<Button>(R.id.fetchDataBtn)
        fetchBtn.setOnClickListener {
            val city = "Manila, PH"
            getWeather(city, "32f5c3218f266aa146cfbc916e5673f5")
        }
    }

    fun getWeather(city: String, apiKey: String) {
        val countryElement = findViewById<TextView>(R.id.countryName)
        val temperatureElement = findViewById<TextView>(R.id.temperatureElement)
        val feelsLikeElement = findViewById<TextView>(R.id.feelsLikeElement)
        val descriptionElement = findViewById<TextView>(R.id.description)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Get Coordinates
                val geo = RetrofitClient.service.getCoordinates(city, 1, apiKey)
                println("Geo response: $geo")

                if(geo.isNotEmpty()) {
                    val lat = geo[0].lat
                    val lon = geo[0].lon
                    val cityName = geo[0].name

                    val weather = RetrofitClient.service.getWeather(lat, lon, apiKey)
                    println("Weather response: $weather")

                    withContext(Dispatchers.Main) {
                        countryElement.text = cityName
                        temperatureElement.text = "Temperature: ${weather.main.temp}°C"
                        feelsLikeElement.text = "Feels like: ${weather.main.feels_like}°F"
                        descriptionElement.text = "${weather.weather[0].description}"
                    }
                } else {
                    println("Geo response is empty")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                println("Error: ${e.message}")
            }
        }
    }
}