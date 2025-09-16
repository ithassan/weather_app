package com.example.myweatherforcast.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myweatherforcast.screens.main.MainScreen
import com.example.myweatherforcast.screens.WeatherScreenSplashScreen
import com.example.myweatherforcast.screens.about.AboutScreen
import com.example.myweatherforcast.screens.favorite.FavoriteScreen
import com.example.myweatherforcast.screens.favorite.FavoriteViewModel
import com.example.myweatherforcast.screens.main.MainViewModel
import com.example.myweatherforcast.screens.search.SearchScreen
import com.example.myweatherforcast.screens.settings.SettingScreen

@Composable
fun WeatherNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = WeatherScreen.SplashScreen.name) {
        composable(WeatherScreen.SplashScreen.name){
            WeatherScreenSplashScreen(navController=navController)
        }
        val route = WeatherScreen.MainScreen.name
        composable("$route/{city}", arguments = listOf(
            navArgument(name = "city"){
                type = NavType.StringType
            }
        )){
            navBack -> navBack.arguments?.getString("city").let { city->
            val mainViewModel = hiltViewModel<MainViewModel>()
            MainScreen(navController=navController, mainViewModel,city = city)
        }

        }

        composable(WeatherScreen.SearchScreen.name){
            SearchScreen(navController=navController)
        }

        composable(WeatherScreen.AboutScreen.name){
            AboutScreen(navController=navController)
        }

        composable(WeatherScreen.FavoriteScreen.name){
            val favoriteViewModel = hiltViewModel<FavoriteViewModel>()
            FavoriteScreen(navController = navController,favoriteViewModel=favoriteViewModel)
        }

        composable(WeatherScreen.SettingScreen.name){
            SettingScreen(navController = navController)
        }
    }
}