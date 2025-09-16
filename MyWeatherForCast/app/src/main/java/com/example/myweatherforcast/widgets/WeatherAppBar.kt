package com.example.myweatherforcast.widgets

import android.content.Context
import android.graphics.drawable.Icon
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myweatherforcast.model.Favorite
import com.example.myweatherforcast.navigation.WeatherScreen
import com.example.myweatherforcast.screens.favorite.FavoriteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherAppBar(
    tittle: String = "Tittle",
    icon: ImageVector? = null,
    isMainScreen: Boolean = true,
    elevation: Dp = 0.dp,
    navController: NavController,
    favoriteViewModel: FavoriteViewModel = hiltViewModel(),
    onAddActionClicked: () ->  Unit = {},
    onButtonClicked: () -> Unit = {}

){
    val showDialog = remember {
        mutableStateOf(false)
    }

    val showIt  = remember {
        mutableStateOf(false)
    }

    val context =  LocalContext.current
    if(showDialog.value){
        ShowSettingDropDownMenu(showDialog = showDialog,navController)
    }
    Surface(tonalElevation = elevation, shadowElevation =elevation) { // Use Surface to add elevation
        TopAppBar(
            title = { Text(tittle, color = MaterialTheme.colorScheme.onBackground,
                       style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 15.sp)
                ) },
            actions = {
                if(isMainScreen) {
                    IconButton(onClick = { onAddActionClicked.invoke() }) {
                        Icon(Icons.Filled.Search, contentDescription = "Settings")
                    }
                    IconButton(onClick = { showDialog.value = true }) {
                        Icon(Icons.Filled.MoreVert, contentDescription = "More Icon")
                    }
                }
                else{
                    Box {}
                }
            },
            navigationIcon = {
                if(icon != null){
                    Icon(imageVector = icon, contentDescription = null, tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.clickable {
                        onButtonClicked.invoke()
                    })
                }
                if(isMainScreen){
                    val isAlreadyFavList = favoriteViewModel.favList.collectAsState().value.filter {
                        item ->
                        item.city == tittle.split(",")[0]
                    }
                    Icon(imageVector = if(isAlreadyFavList.isEmpty())Icons.Default.FavoriteBorder else Icons.Default.Favorite, contentDescription = "Fav icon", modifier = Modifier
                        .scale(0.9f)
                        .clickable {
                            favoriteViewModel.insertFavorite(
                                Favorite(
                                    tittle.split(",")[0],
                                    tittle.split(",")[1]
                                )
                            ).run {
                                showIt.value =true
                            }
                        }, tint = Color.Red.copy(alpha = 0.6f))

                    ShowToast(context,showIt)
                }
            }
            ,
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent // Set your desired background color here
            )
        )
    }

}

@Composable
fun ShowSettingDropDownMenu(showDialog: MutableState<Boolean>, navController: NavController) {
    var expanded by remember {
     mutableStateOf(true)
    }
    val items = listOf("About","Favorites","Settings")
    Column(modifier = Modifier
        .fillMaxWidth()
        .wrapContentSize(Alignment.TopEnd)
        .absolutePadding(top = 45.dp, right = 20.dp)
    ) {
        DropdownMenu(expanded = expanded, onDismissRequest = {
            expanded = false
            showDialog.value = false }) {
            items.forEachIndexed { index, text ->
                DropdownMenuItem(text = { Text(text = text) }, leadingIcon = {
                    Icon(imageVector =
                    when(text){
                        "About" -> Icons.Default.Info
                       "Favorites" -> Icons.Default.FavoriteBorder
                        else -> Icons.Default.Settings
                    }, contentDescription = "")
                },onClick = {
                    showDialog.value = false
                    expanded = false
                    when(text) {
                        "About" -> navController.navigate(WeatherScreen.AboutScreen.name)
                        "Favorites" -> navController.navigate(WeatherScreen.FavoriteScreen.name)
                        else -> navController.navigate(WeatherScreen.SettingScreen.name)
                    }
                })
            }
        }
    }
}

fun ShowToast(context: Context,showIt: MutableState<Boolean>){
    if(showIt.value){
        Toast.makeText(context,"City Added to Favorites",Toast.LENGTH_SHORT).show()
        showIt.value = false
    }
}