package data.service

import data.model.search.CitySearchModel
import data.model.weather_detail.CurrentWeather
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

class ApiService {
    @OptIn(ExperimentalSerializationApi::class)
    private val httpClient = HttpClient() {
        install(ContentNegotiation) {
            json(
                Json{
                    explicitNulls = false
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                }
            )
        }
    }
    suspend fun searchCity(name: String): CitySearchModel {
           return httpClient.get("https://geocoding-api.open-meteo.com/v1/search") {
                parameter("name", name)
            }.body<CitySearchModel>()
    }
    suspend fun getCurrentWeather(latitude : String, longitude : String,current : Boolean) : CurrentWeather{
        return httpClient.get("https://api.open-meteo.com/v1/forecast"){
            parameter("latitude",latitude)
            parameter("longitude",longitude)
            parameter("current_weather",current)
        }.body<CurrentWeather>()
    }
}