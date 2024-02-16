package view

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import viewmodel.weather_detail.WeatherDetailViewModel

data class WeatherDetailScreen(
    val latitude : Double,
    val longitude : Double,
    val current : Boolean,
) : Screen {
    @Composable
    override fun Content() {
        WeatherDetailWidget()
    }
    @Composable
    fun WeatherDetailWidget(){
        val weatherDetailViewModel = getViewModel(Unit, viewModelFactory { WeatherDetailViewModel() })
        val weatherDetailState = weatherDetailViewModel.currentState.collectAsState()
        val navigator = LocalNavigator.currentOrThrow
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

        }
    }
}