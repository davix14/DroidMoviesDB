package com.example.droidmoviesdb.domain.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MainSearchByManyResponse(
    val mainObj: SearchByMany
)
