package com.example.myweatherforcast.di

import android.content.Context
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Room
import com.example.myweatherforcast.data.WeatherDao
import com.example.myweatherforcast.data.WeatherDataBase
import com.example.myweatherforcast.network.WeatherApi
import com.example.myweatherforcast.utills.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideOpenWeatherApi():WeatherApi{
        return Retrofit.Builder()
            .baseUrl(Constants.baseUrl)
            .addConverterFactory(GsonConverterFactory.create()) // Gson converter
            .build()
            .create(WeatherApi::class.java)
    }

    @Provides
    @Singleton
    fun provideWeatherDao(weatherDataBase: WeatherDataBase):WeatherDao = weatherDataBase.weatherDao()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): WeatherDataBase
    = Room.databaseBuilder(
        context,
        WeatherDataBase::class.java,
        "Weather_database"
    ).fallbackToDestructiveMigration().build()
}