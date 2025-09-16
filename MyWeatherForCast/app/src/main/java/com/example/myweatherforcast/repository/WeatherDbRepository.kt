package com.example.myweatherforcast.repository

import com.example.myweatherforcast.data.WeatherDao
import com.example.myweatherforcast.model.Favorite
import com.example.myweatherforcast.model.Units
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherDbRepository @Inject constructor(private val weatherDao: WeatherDao) {
    fun  getFavorites(): Flow<List<Favorite>> = weatherDao.getFavorites()
   suspend fun insertFavorite(favorite: Favorite) = weatherDao.insertFavorite(favorite)
    suspend fun updateFavorite(favorite: Favorite) =weatherDao.updateFavorite(favorite)
    suspend fun deleteFavorite(favorite: Favorite) =weatherDao.deleteFavorite(favorite)
    suspend fun getFavById(city:String):Favorite = weatherDao.getFavById(city)!!


    fun  getUnits(): Flow<List<Units>> = weatherDao.getUnits()
    suspend fun insertUnit(units: Units) = weatherDao.insertUnit(units)
    suspend fun updateUnit(units: Units) =weatherDao.updateUnit(units)
    suspend fun deleteUnits() =weatherDao.deleteUnits()


}