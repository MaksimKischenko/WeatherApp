package com.example.londonweaher.models

data class WeatherModel(
    val city: String = "",
    var timeDate: String = "dd.MM.yyyy HH:mm",
    val currentTemp: String = "",
    val maxTemp: String = "",
    val minTemp: String = "",
    val description: String = "",
    val mainDesc: String = "",
    val windSpeed: String = "",
    val sunrise: String = "dd.MM.yyyy HH:mm",
    val sunset: String = "dd.MM.yyyy HH:mm",
    val pressure: String = "",
    val icon: String = ""
) 
