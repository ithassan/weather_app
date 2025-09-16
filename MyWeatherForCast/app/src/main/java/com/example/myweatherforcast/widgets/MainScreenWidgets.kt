package com.example.myweatherforcast.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.myweatherforcast.R
import com.example.myweatherforcast.model.WeatherItem
import com.example.myweatherforcast.utills.formatDate
import com.example.myweatherforcast.utills.formatDateTime
import com.example.myweatherforcast.utills.formatDecimals

@Composable
fun WeatherDetailRow(item: WeatherItem) {
    val imageUrl = "https://openweathermap.org/img/wn/${item.weather[0].icon}.png"

    Surface(
        Modifier
            .padding(3.dp)
            .fillMaxWidth(),
        shape = CircleShape.copy(topEnd = CornerSize(6.dp)),
        color = Color.White
    ) {
        Row (modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(text = formatDate(item.dt).split(",")[0], modifier = Modifier.padding(start = 15.dp))
            WeatherStateImage(imageUrl = imageUrl)
            Surface(modifier = Modifier.padding(0.dp),
                shape = CircleShape,
                color = Color(0xFFEEB908)
            ) {
                Text(text = item.weather[0].description, modifier = Modifier.padding(4.dp),style = MaterialTheme.typography.bodySmall)
            }
            Text(text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color.Blue.copy(alpha = 0.7f),
                        fontWeight = FontWeight.SemiBold,
                    )
                ){
                    append(formatDecimals(item.temp.max) +"º")
                }
                withStyle(
                    style = SpanStyle(
                        color = Color.LightGray,
                        fontWeight = FontWeight.SemiBold,
                    )
                ){
                    append(formatDecimals(item.temp.min) +"º")
                }
            }, modifier = Modifier.padding(end = 15.dp))
        }

    }
}

@Composable
fun SunSetSunRise(weather: WeatherItem) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 15.dp, bottom = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween){
        Row( verticalAlignment = Alignment.CenterVertically){
            Image(painter = painterResource(id = R.drawable.sunrise), contentDescription = "sunrise", modifier = Modifier.size(25.dp))
            Text(text = formatDateTime(weather.sunrise) +" ", style = MaterialTheme.typography.bodySmall)
        }
        Row( verticalAlignment = Alignment.CenterVertically){
            Text(text = formatDateTime(weather.sunset) +" ", style = MaterialTheme.typography.bodySmall)
            Image(painter = painterResource(id = R.drawable.sunset), contentDescription = "sunset", modifier = Modifier.size(25.dp))

        }
    }
}

@Composable
fun WeatherStateImage(imageUrl: String) {
    Image(painter = rememberImagePainter(data = imageUrl), contentDescription ="Icon Image",
        modifier = Modifier.size(80.dp)
    )
}
@Composable
fun HumidityWInPressureRow(weather: WeatherItem,isImperial:Boolean){
    Row (modifier = Modifier
        .padding(12.dp)
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Row(modifier = Modifier.padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(painter = painterResource(id = R.drawable.humidity), contentDescription ="Humidity", modifier = Modifier.size(20.dp) )
            Text(text = " ${weather.humidity}%", style = MaterialTheme.typography.bodySmall)
        }
        Row(modifier = Modifier.padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(painter = painterResource(id = R.drawable.pressure), contentDescription ="pressure", modifier = Modifier.size(20.dp) )
            Text(text = " ${weather.pressure} psi", style = MaterialTheme.typography.bodySmall)
        }
        Row(modifier = Modifier.padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(painter = painterResource(id = R.drawable.wind), contentDescription ="wind", modifier = Modifier.size(20.dp) )
            Text(text = " ${formatDecimals( weather.speed)} "+if(isImperial)"mph" else "m/s", style = MaterialTheme.typography.bodySmall)
        }
    }
}
