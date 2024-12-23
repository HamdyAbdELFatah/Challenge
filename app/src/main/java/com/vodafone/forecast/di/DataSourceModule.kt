package com.vodafone.forecast.di

import com.vodafone.data.local.datasource.WeatherLocalDataSource
import com.vodafone.data.local.datasource.WeatherLocalDataSourceImpl
import com.vodafone.data.remote.datasource.WeatherRemoteDataSource
import com.vodafone.data.remote.datasource.WeatherRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun provideLocalDataSource(weatherLocalDataSourceImpl: WeatherLocalDataSourceImpl): WeatherLocalDataSource

    @Binds
    @Singleton
    abstract fun provideRemoteDataSource(weatherRemoteDataSourceImpl: WeatherRemoteDataSourceImpl): WeatherRemoteDataSource

}