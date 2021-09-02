package com.example.newsapi.services

import com.example.newsapi.endpoints.NewsApiEndpoints
import com.example.newsapi.models.NewsList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

internal interface NewsapiService {
    @GET(NewsApiEndpoints.everything)
    suspend fun getNewsFor(@Query("q") query: String): Response<NewsList>
}