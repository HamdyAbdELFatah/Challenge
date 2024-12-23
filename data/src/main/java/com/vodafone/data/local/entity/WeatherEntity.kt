package com.vodafone.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WeatherEntity(
    @PrimaryKey val cityName: String,
    val currentTemperature: Double,
    val condition: String,
    val icon: String,
    val lastUpdated: Long
)