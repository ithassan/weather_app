package com.example.myweatherforcast.screens.favorite

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myweatherforcast.model.Favorite
import com.example.myweatherforcast.repository.WeatherDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repository: WeatherDbRepository) : ViewModel() {

    private val _favList = MutableStateFlow<List<Favorite>>(emptyList())
    val favList = _favList.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getFavorites().distinctUntilChanged().collect{
                listofFav ->
                if(listofFav.isEmpty()){
                    Log.d("Fav","Empty List")
                }else{
                    _favList.value = listofFav
                }
            }
        }
    }

    fun insertFavorite(favorite: Favorite) =
        viewModelScope.launch {
            repository.insertFavorite(favorite)
        }

    fun updateFavorite(favorite: Favorite) =
        viewModelScope.launch {
            repository.updateFavorite(favorite)
        }

    fun deleteFavorite(favorite: Favorite) =
        viewModelScope.launch {
            repository.deleteFavorite(favorite)
        }

}