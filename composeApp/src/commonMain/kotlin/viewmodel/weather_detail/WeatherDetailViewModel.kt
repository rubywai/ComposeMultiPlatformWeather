package viewmodel.weather_detail

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import data.service.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class WeatherDetailViewModel : ScreenModel,KoinComponent {
    private val _currentState = MutableStateFlow<WeatherDetailState>(WeatherDetailLoadingState)
    val currentState = _currentState.asStateFlow()
    private val apiService by inject<ApiService>()
    init {
        print("view model is created")
    }
    fun getCurrentWeather(latitude : String,longitude : String,current : Boolean){
        screenModelScope.launch {
            _currentState.value = WeatherDetailLoadingState
            try{
                val weatherDetail = apiService.getCurrentWeather(latitude,longitude,current)
                _currentState.update {
                    WeatherDetailSuccess(weatherDetail)
                }
            }
            catch (e: Exception){
                _currentState.update {
                    WeatherDetailFailed("Something Wrong")
                }
            }
        }
    }

    override fun onDispose() {
        print("view model is destroyed");
        super.onDispose()
    }
}