package com.vodafone.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vodafone.data.local.entity.WeatherEntity


@Dao
interface WeatherDao {
    @Query("SELECT * FROM WeatherEntity WHERE cityName = :cityName LIMIT 1")
    suspend fun getWeatherByCity(cityName: String): WeatherEntity?

    @Query("SELECT * FROM WeatherEntity ORDER BY lastUpdated DESC LIMIT 1")
    suspend fun getLastWeather(): WeatherEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWeather(weatherEntity: WeatherEntity)
}