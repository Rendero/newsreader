package com.example.newsapi.managers

import com.example.newsapi.BuildConfig
import com.example.newsapi.Config
import com.example.newsapi.endpoints.NewsApiEndpoints
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.*
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

internal class NetworkManager {

    // Retrofit
    inline fun <reified T> createRetrofit(): T {
        val retrofit = Retrofit.Builder()
            .baseUrl(NewsApiEndpoints.baseurl)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(OkHttpClient.Builder()
                .addInterceptor { chain ->
                    chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer ${Config.NEWSAPI_KEY}")
                        .build()
                        .let(chain::proceed)
                }
                .addInterceptor(logging.invoke())
                .build())
            .build()

        return retrofit.create(T::class.java)
    }

    // Okhttp
    private val logging : () -> HttpLoggingInterceptor = {
        val level = if(BuildConfig.DEBUG) Level.BODY else Level.NONE
        HttpLoggingInterceptor().setLevel(level)
    }

}