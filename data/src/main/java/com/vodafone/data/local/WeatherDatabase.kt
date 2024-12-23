package com.vodafone.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vodafone.data.local.entity.WeatherEntity

@Database(entities = [WeatherEntity::class], version = 1)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}