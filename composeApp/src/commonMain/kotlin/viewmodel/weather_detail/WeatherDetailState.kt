package viewmodel.weather_detail

import data.model.weather_detail.CurrentWeather

sealed class WeatherDetailState

data object  WeatherDetailLoadingState : WeatherDetailState()

class WeatherDetailSuccess(val weather: CurrentWeather) : WeatherDetailState()
class WeatherDetailFailed(val errorMessage : String) : WeatherDetailState()