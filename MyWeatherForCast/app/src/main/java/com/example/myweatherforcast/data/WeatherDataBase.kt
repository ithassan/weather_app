package com.example.myweatherforcast.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myweatherforcast.model.Favorite
import com.example.myweatherforcast.model.Units

@Database(entities = [Favorite::class,Units::class], version = 2, exportSchema = false)
abstract class WeatherDataBase:RoomDatabase() {
    abstract fun weatherDao() : WeatherDao
}