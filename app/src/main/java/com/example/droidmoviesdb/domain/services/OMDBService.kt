package com.example.droidmoviesdb.domain.services

import com.example.droidmoviesdb.domain.models.MainSearchByManyResponse
import com.example.droidmoviesdb.domain.models.SearchByMany
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface OMDBService {

    @GET(".")
    suspend fun searchByTitle(
        @Query("apikey") key: String,
        @Query("s") searchIn: String
    ): SearchByMany

}