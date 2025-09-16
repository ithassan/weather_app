package com.example.myweatherforcast.screens.settings

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myweatherforcast.model.Favorite
import com.example.myweatherforcast.model.Units
import com.example.myweatherforcast.repository.WeatherDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val repository: WeatherDbRepository) : ViewModel() {

    private val _unitList = MutableStateFlow<List<Units>>(emptyList())
    val favList = _unitList.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getUnits().distinctUntilChanged().collect{
                listOfUnit ->
                if(listOfUnit.isEmpty()){
                    Log.d("Unit","Empty List")
                }else{
                    _unitList.value = listOfUnit
                }
            }
        }
    }

    fun insertUnit(units: Units) =
        viewModelScope.launch {
            repository.insertUnit(units)
        }

    fun updateFavorite(units: Units) =
        viewModelScope.launch {
            repository.updateUnit(units)
        }

    fun deleteUnits() =
        viewModelScope.launch {
            repository.deleteUnits()
        }

}