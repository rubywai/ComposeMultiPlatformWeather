package data.model.weather_detail

import kotlinx.serialization.Serializable

@Serializable
data class CurrentWeatherX(
    val interval: Int,
    val is_day: Int,
    val temperature: Double,
    val time: String,
    val weathercode: Int,
    val winddirection: Int,
    val windspeed: Double
)