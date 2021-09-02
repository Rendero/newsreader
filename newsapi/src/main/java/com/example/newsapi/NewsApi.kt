package com.example.newsapi

import com.example.newsapi.managers.NetworkManager
import com.example.newsapi.models.NewsList
import com.example.newsapi.services.NewsapiService

object NewsApi {

    suspend fun getNewsList(query: String): NewsList? {

        // TODO: Refactor to top level, temporary in function scope to prevent issues
        val service by lazy { NetworkManager().createRetrofit<NewsapiService>() }

        val result = service.getNewsFor(query)

        return when(result.code()) {
            200 -> { result.body()}
            else -> null
        }
    }
}