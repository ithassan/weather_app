package com.example.myweatherforcast.screens.main

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myweatherforcast.data.DateOrException
import com.example.myweatherforcast.model.Weather
import com.example.myweatherforcast.model.WeatherItem
import com.example.myweatherforcast.navigation.WeatherScreen
import com.example.myweatherforcast.screens.settings.SettingsViewModel
import com.example.myweatherforcast.utills.formatDate
import com.example.myweatherforcast.utills.formatDecimals
import com.example.myweatherforcast.widgets.HumidityWInPressureRow
import com.example.myweatherforcast.widgets.SunSetSunRise
import com.example.myweatherforcast.widgets.WeatherAppBar
import com.example.myweatherforcast.widgets.WeatherDetailRow
import com.example.myweatherforcast.widgets.WeatherStateImage

@Composable
fun MainScreen(navController: NavController, mainViewModel: MainViewModel,settingsViewModel: SettingsViewModel = hiltViewModel(), city: String?){
  val unitFromDb = settingsViewModel.favList.collectAsState().value
    var unit by remember {
        mutableStateOf("imperial")
    }
    var isImperial by remember {
        mutableStateOf(false)
    }
    if(unitFromDb.isNotEmpty()){
        unit = unitFromDb[0].unit.split(" ")[0].lowercase()
        isImperial = unit == "imperial"
    }
    val weatherData = produceState<DateOrException<Weather,Boolean,Exception>>(
        initialValue = DateOrException(loading = true)){
        value = mainViewModel.getWeather(city.toString(),units =unit)
    }.value
    if(weatherData.loading == true){
        CircularProgressIndicator()
    }else if (weatherData.data != null){
       MainScaffold(weather = weatherData.data!!,navController,isImperial)
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScaffold(weather: Weather, navController: NavController,isImperial:Boolean) {
    Scaffold(topBar = {
        WeatherAppBar(tittle = weather.city.name+", ${weather.city.country}",
          //  icon = Icons.Default.ArrowBack,
            navController = navController,
            onAddActionClicked = {
                navController.navigate(WeatherScreen.SearchScreen.name)
            },
            elevation = 5.dp
        ){
         Log.d("testcall","click")
        }
    }) {
            paddingValues -> // Using paddingValues to adjust content
        MainContent(data = weather, modifier = Modifier.padding(paddingValues),isImperial)
    }

}


@SuppressLint("SuspiciousIndentation")
@Composable
fun MainContent(data: Weather, modifier: Modifier,isImperial:Boolean) {
    val weatherItem = data.list[0]
    val imageUrl = "https://openweathermap.org/img/wn/${weatherItem.weather[0].icon}.png"

        Column(
            modifier
                .padding(4.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = formatDate(weatherItem.dt),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(6.dp)
            )
            Surface(modifier = Modifier
                .padding(4.dp)
                .size(200.dp),
            shape = CircleShape,
                color = Color(0xFFEEB908)
                ) {
            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                WeatherStateImage(imageUrl = imageUrl)
                Text(
                    text = formatDecimals( weatherItem.temp.day)+"º",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.ExtraBold,
                )
                Text(
                    text = weatherItem.weather[0].main,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontStyle = FontStyle.Italic,
                )

            }
            }
            HumidityWInPressureRow(weather = data.list[0],isImperial)
            Divider()
            SunSetSunRise(weather = data.list[0])
            Text(text = "This Week",
                style = MaterialTheme.typography.headlineMedium
                )
            Surface(modifier= Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
                color = Color(0xFFEEF1EF),
                shape = RoundedCornerShape(size = 14.dp)
                ) {
                LazyColumn(modifier = Modifier.padding(2.dp),
                    contentPadding = PaddingValues(1.dp)
                    ) {
                    items(items = data.list){item: WeatherItem ->  
                        WeatherDetailRow(item)
                    }
                }
            }

        }
}

