package com.example.apiconnectapp.test

import org.junit.Assert.assertEquals
import org.junit.Test

class WeatherFetcher {
    fun convertToFahrenheit(celcius: Int): Int {
        return (celcius * 9 / 5) + 32
    }
}