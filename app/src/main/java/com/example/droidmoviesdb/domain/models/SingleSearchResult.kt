package com.example.droidmoviesdb.domain.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SingleSearchResult(
    @field:Json(name = "Title") val Title: String,
    @Json(name = "Year") val year: String,
    @Json(name = "imdbID") val imdbId: String,
    @Json(name = "Type") val type: String,
    @Json(name = "Poster") val poster: String
)