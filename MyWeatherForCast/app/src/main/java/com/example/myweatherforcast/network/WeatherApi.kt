package com.example.myweatherforcast.network

import com.example.myweatherforcast.model.Weather
import com.example.myweatherforcast.model.WeatherObject
import com.example.myweatherforcast.utills.Constants
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface WeatherApi {
    @GET(value = "data/2.5/forecast/daily")
    suspend fun getWeather(@Query("q") q:String,
                           @Query("units") units:String = "imperial",
                           @Query("appid") appid:String = Constants.API_KEY
    ) : Weather
}