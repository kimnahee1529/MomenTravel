package com.android.traveltube.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {
    private const val IMAGE_BASE_URL = "https://www.googleapis.com/youtube/v3/"

    val api: YouTubeAPI by lazy { retrofit.create(YouTubeAPI::class.java)}

    private fun createOkHttpClient(): OkHttpClient {
        val intercepter = HttpLoggingInterceptor()

        return OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .addNetworkInterceptor(intercepter)
            .build()
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(IMAGE_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(createOkHttpClient())
        .build()

}