package data.model.search

import kotlinx.serialization.Serializable

@Serializable
data class CitySearchModel(
    val generationtime_ms: Double?,
    val results: List<Result>?
)