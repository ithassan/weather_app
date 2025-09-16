package com.example.myweatherforcast.repository

import android.util.Log
import com.example.myweatherforcast.data.DateOrException
import com.example.myweatherforcast.model.Weather
import com.example.myweatherforcast.network.WeatherApi
import javax.inject.Inject

class WeatherRepository @Inject constructor(private  val weatherApi: WeatherApi) {
    suspend fun getWeather(cityQuery: String, units: String): DateOrException<Weather,Boolean, Exception> {
        val response = try {
            weatherApi.getWeather(cityQuery, units = units)
        }catch (e:Exception){
            Log.d("Inside","getWeather : ${e.toString()}")
            return DateOrException(e = e)
        }
        return DateOrException(data = response)

        Log.d("Inside","getWeather : ${response.toString()}")
    }
}