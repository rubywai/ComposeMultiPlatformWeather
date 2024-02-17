package view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import utils.toCondition
import utils.toEmoji
import viewmodel.weather_detail.WeatherDetailFailed
import viewmodel.weather_detail.WeatherDetailLoadingState
import viewmodel.weather_detail.WeatherDetailSuccess
import viewmodel.weather_detail.WeatherDetailViewModel

data class WeatherDetailScreen(
    val latitude : Double,
    val longitude : Double,
    val current : Boolean,
    val cityName : String,
) : Screen {
    @Composable
    override fun Content() {
        WeatherDetailWidget()
    }
    @Composable
    fun WeatherDetailWidget(){
        val weatherDetailViewModel = getViewModel(Unit, viewModelFactory { WeatherDetailViewModel() })
        val weatherDetailState by weatherDetailViewModel.currentState.collectAsState()
        val navigator = LocalNavigator.currentOrThrow
        LaunchedEffect(weatherDetailViewModel) {
            weatherDetailViewModel.getCurrentWeather(latitude.toString(),longitude.toString(),current)
        }
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text("Weather Application")
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            navigator.pop()
                        }){
                            Icon(Icons.Filled.ArrowBack, contentDescription = "back button")
                        }
                    }

                )
            },
        ) {
            when (weatherDetailState) {
               is WeatherDetailLoadingState -> {
                   Box(
                       Modifier.fillMaxSize(),
                       contentAlignment = Alignment.Center
                   ) {
                       CircularProgressIndicator()
                   }
               }

                is WeatherDetailFailed -> Text("Failed")
                is WeatherDetailSuccess -> Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    val result = (weatherDetailState as WeatherDetailSuccess).weather
                    val currentWeatherUnit = result.current_weather_units
                    val currentWeather = result.current_weather
                    Text(cityName)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        Text("${currentWeather.temperature} ${currentWeatherUnit.temperature}")
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(currentWeather.weathercode.toCondition(), style = TextStyle(fontSize = 30.sp))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(currentWeather.weathercode.toCondition().toEmoji(), style = TextStyle(fontSize = 30.sp))

                }
            }
        }
    }
}