package data.service

import data.model.CitySearchModel
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
}