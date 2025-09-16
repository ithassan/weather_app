package com.example.myweatherforcast.screens.about

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.example.myweatherforcast.R
import com.example.myweatherforcast.widgets.WeatherAppBar

@Composable
fun AboutScreen(navController: NavController){
    Scaffold(topBar = {WeatherAppBar(navController = navController,
        tittle = "About", icon =
        Icons.Default.ArrowBack,
        isMainScreen = false)}) {paddingVlaues ->
        Surface(modifier = Modifier
            .padding(paddingVlaues)
            .fillMaxWidth()
            .fillMaxHeight()) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Text(text = stringResource(id = R.string.about_name), style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                Text(text = stringResource(id = R.string.profession), style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Normal, textAlign = TextAlign.Center)
            }
        }
    }
}