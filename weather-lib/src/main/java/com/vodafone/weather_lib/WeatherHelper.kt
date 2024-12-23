package com.vodafone.weather_lib
const val BASE_WEATHER_ICON_URL = "https://api.openweathermap.org/data/3.0/img/w/"

fun String.getIconUrl() : String =
                "$BASE_WEATHER_ICON_URL${this}@2x.png"

