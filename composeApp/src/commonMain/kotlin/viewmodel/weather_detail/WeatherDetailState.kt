package viewmodel.weather_detail

import data.model.weather_detail.CurrentWeather

sealed class WeatherDetailState

data object  WeatherDetailLoadingState : WeatherDetailState()

class WeatherDetailSuccess(weather: CurrentWeather) : WeatherDetailState()
class WeatherDetailFailed(errorMessage : String) : WeatherDetailState()