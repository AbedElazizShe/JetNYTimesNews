package com.example.jetnytimesnews.api

import android.os.SystemClock
import com.example.jetnytimesnews.BuildConfig
import com.example.jetnytimesnews.data.network.NYTimesNewsResponse
import okhttp3.Dispatcher
import okhttp3.Interceptor
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

            val dispatcher = Dispatcher().apply {
                maxRequests = 1
            }

            // Avoid quickly hitting rate limit
            val delayInterceptor = Interceptor { chain ->
                SystemClock.sleep(3000)
                chain.proceed(chain.request())
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .addInterceptor(delayInterceptor)
                .dispatcher(dispatcher)
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
