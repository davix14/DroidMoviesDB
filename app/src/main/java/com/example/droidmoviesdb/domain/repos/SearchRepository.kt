package com.example.droidmoviesdb.domain.repos

import android.util.Log
import com.example.droidmoviesdb.domain.models.SearchByMany
import com.example.droidmoviesdb.domain.services.OMDBService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class SearchRepository {
    private final val LOG_TAG = SearchRepository::class.java.simpleName
    private val apiKey = "22df56fe"

    private val baseUrl = "https://www.omdbapi.com/"
    private val moshi: Moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }
    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(SearchApiKeyInterceptor())
            .build()
    }

    private val omdbService: OMDBService by lazy {
        retrofit.create(OMDBService::class.java)
    }

    var latestResponse: PublishSubject<SearchByMany> = PublishSubject.create()

    suspend fun getSearchResults(searchIn: String) {
        var response: SearchByMany? = null
        withContext(Dispatchers.IO) {
            try {
                response = omdbService.searchByTitle(apiKey, searchIn)
            }catch (e: Exception){
                Log.d(LOG_TAG, "Failed to get search results due tot erropr:\n${e.printStackTrace()}")
            }
        }
        if(response?.Search?.isNotEmpty() == true)
            response?.let { latestResponse.onNext(it) }
    }

    class SearchApiKeyInterceptor: Interceptor {
        private val LOG_TAG = SearchApiKeyInterceptor::class.java.simpleName
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            request.url.newBuilder().addQueryParameter("apikey", "22df56fe")
            Log.d(LOG_TAG,"Url after adding apiKey: ${request.url}")
            return chain.proceed(request)
        }

    }

}