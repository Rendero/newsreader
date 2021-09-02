package com.example.newsreader.models

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.newsapi.NewsApi
import com.example.newsapi.models.Article
import com.example.newsreader.managers.NetworkManager
import com.example.newsreader.repositories.LocalCacheRepository
import kotlinx.coroutines.*

class MainViewModel(private val context: Context) {

    val articlesLiveData = MutableLiveData<List<Article>>()

    fun getNewsListFor(keywords: List<String>) {
        if(NetworkManager.hasConnectivity()) {
            fetchNewsArticles(keywords)
        } else {
            LocalCacheRepository(context).getArticlesFromCache().let {
                articlesLiveData.value = it
            }
        }
    }

    private fun fetchNewsArticles(keywords: List<String>) {

        CoroutineScope(Dispatchers.IO).launch {

            var joinedArticles: List<Article> = emptyList()

            val newsArticles = keywords.map {
                async { NewsApi.getNewsList(it) }
            }

            newsArticles.awaitAll().let { response ->
                for (result in response) {
                    result?.articles?.run {
                        joinedArticles = joinedArticles + this
                    }
                }

                joinedArticles = joinedArticles.sortedBy { it.publishedAt }

                withContext(Dispatchers.Main) {
                    LocalCacheRepository(context).saveArticlesToCache(joinedArticles)
                    articlesLiveData.value = joinedArticles
                }
            }
        }
    }
}
