package com.example.apiconnectapp.test

import org.junit.Assert.assertEquals
import org.junit.Test

class WeatherFetcherTest {
    @Test
    fun testTemperatureConversion() {
        val fetcher = WeatherFetcher()
        val celcius = 25
        val expectedFahrenheit = 77
        val actual = fetcher.convertToFahrenheit(celcius)
        System.out.println("Actual Fahrenheit: $actual")
        assertEquals(expectedFahrenheit, actual)
    }
}