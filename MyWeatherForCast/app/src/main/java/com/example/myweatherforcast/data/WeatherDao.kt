package com.example.myweatherforcast.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.myweatherforcast.model.Favorite
import com.example.myweatherforcast.model.Units
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Query(value = "SELECT * from fav_tbl")
    fun getFavorites(): Flow<List<Favorite>>

    @Query("SELECT * from fav_tbl where city =:city")
    suspend fun getFavById(city:String): Favorite?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend  fun insertFavorite(favorite: Favorite)

    @Update(onConflict = OnConflictStrategy.REPLACE)
   suspend  fun updateFavorite(favorite: Favorite)

    @Query(value = "DELETE from fav_tbl")
     suspend fun deleteFavorite()

    @Delete
    suspend fun deleteFavorite(favorite: Favorite) : Unit

    @Query(value = "SELECT * from setting_tbl")
     fun getUnits(): Flow<List<Units>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend  fun insertUnit(units: Units)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend  fun updateUnit(units: Units)

    @Query(value = "DELETE from setting_tbl")
    suspend fun deleteUnits()

    @Delete
    suspend fun deleteUnit(units: Units) : Unit

}