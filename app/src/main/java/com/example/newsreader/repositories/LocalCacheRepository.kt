package com.example.newsreader.repositories

import android.content.Context
import com.example.newsapi.models.Article
import org.json.JSONObject

// TODO: Temporary quick solution for local storage. Needs to be replaced with more robust storage to file
class LocalCacheRepository(private val context: Context) {

    fun saveArticlesToCache(articleList: List<Article>) {

        val sharedPref = context.getSharedPreferences("NEWSREADER", Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {

            val articleSet: Set<String> = articleList
                .subList(0, 40)
                .map {
                    "{\"title\":\"${it.title}\",\"urlToImage\":\"${it.urlToImage}\",\"content\":\"${it.content}\"}"
                }.toSet()

            putStringSet("articlelist", articleSet)
            apply()
        }
    }

    fun getArticlesFromCache(): List<Article> {
        val sharedPref = context.getSharedPreferences("NEWSREADER", Context.MODE_PRIVATE)
        val cachedData = sharedPref.getStringSet("articlelist", emptySet())

        if (cachedData != null) {
            return cachedData.map {
                val json = JSONObject(it)

                Article(
                    author = json.optString("author", "author"),
                    title = json.optString("title", "title"),
                    description = json.optString("description", "description"),
                    url = json.optString("url", "url"),
                    urlToImage = json.optString("urlToImage", "urlToImage"),
                    publishedAt = json.optString("publishedAt", "publishedAt"),
                    content = json.optString("content", "content")
                )
            }
        } else {
            return emptyList()
        }
    }
}