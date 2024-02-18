package viewmodel.weather_detail

import data.service.ApiService
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class WeatherDetailViewModel : ViewModel(),KoinComponent {
    private val _currentState = MutableStateFlow<WeatherDetailState>(WeatherDetailLoadingState)
    val currentState = _currentState.asStateFlow()
    private val apiService by inject<ApiService>()
    fun getCurrentWeather(latitude : String,longitude : String,current : Boolean){
        viewModelScope.launch {
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
}