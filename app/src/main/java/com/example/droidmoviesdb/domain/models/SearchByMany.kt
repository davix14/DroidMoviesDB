package com.example.droidmoviesdb.domain.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchByMany(
    @field:Json(name = "Search") val Search: List<SingleSearchResult>,
    @Json(name = "totalResults") val totalResults: Int,
    @Json(name = "Response") val response: String
)
