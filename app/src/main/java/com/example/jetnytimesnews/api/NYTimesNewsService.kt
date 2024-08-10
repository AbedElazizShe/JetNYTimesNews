package com.example.jetnytimesnews.api

import com.example.jetnytimesnews.BuildConfig
import com.example.jetnytimesnews.data.network.NYTimesNewsResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NYTimesNewsService {

    @GET("${BASE_URL}svc/topstories/v2/{category}.json")
    suspend fun getNews(
        @Path("category") category: String,
        @Query("api-key") apiKey: String = BuildConfig.NY_TIMES_API_KEY
    ): NYTimesNewsResponse

    companion object {
        private const val BASE_URL = "https://api.nytimes.com/"

        fun create(): NYTimesNewsService {
            val logger = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NYTimesNewsService::class.java)
        }
    }
}
