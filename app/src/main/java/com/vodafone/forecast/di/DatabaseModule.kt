package com.vodafone.forecast.di

import android.content.Context
import androidx.room.Room
import com.vodafone.core.utils.Constants.DATABASE_NAME
import com.vodafone.data.local.WeatherDao
import com.vodafone.data.local.WeatherDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideWeatherDao(appDatabase: WeatherDatabase): WeatherDao {
        return appDatabase.weatherDao()
    }


    @Provides
    @Singleton
    fun provideWeatherDatabase(@ApplicationContext appContext: Context): WeatherDatabase {
        return Room.databaseBuilder(
            appContext,
            WeatherDatabase::class.java,
            DATABASE_NAME
        ).build()
    }
}