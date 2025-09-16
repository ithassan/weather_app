package com.example.myweatherforcast.screens.main

import androidx.lifecycle.ViewModel
import com.example.myweatherforcast.data.DateOrException
import com.example.myweatherforcast.model.Weather
import com.example.myweatherforcast.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: WeatherRepository) : ViewModel(){
    /*val data : MutableState<DateOrException<Weather,Boolean,Exception>>
     = mutableStateOf(DateOrException(null,true,Exception("")))

    init {
        loadWeather()
    }

    private fun loadWeather(){
     getWeather("Koblenz")
    }

    private fun getWeather(city:String){
        viewModelScope.launch {
            if(city.isEmpty())return@launch
            data.value.loading = true
            data.value = repository.getWeather(cityQuery = city)
            if(data.value.data.toString().isNotEmpty()) data.value.loading = false
            Log.d("GET","getWeather : ${data.value.data.toString()}")
        }


    }*/

    suspend fun getWeather(city: String, units: String): DateOrException<Weather,Boolean,Exception>{
        return repository.getWeather(cityQuery = city,units=units)
    }
}