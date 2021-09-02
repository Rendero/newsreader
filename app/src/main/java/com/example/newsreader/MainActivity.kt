package com.example.newsreader

import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapi.models.Article
import com.example.newsreader.adapter.ArticlesAdapter
import com.example.newsreader.models.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var articleAdapter: ArticlesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerView()
        initViewModel()
    }

    override fun onResume() {
        super.onResume()
        fetchNewsList()
    }

    private fun initRecyclerView() {
        val recyclerView: RecyclerView = findViewById(R.id.ArticlesRecycleview)
        val layoutManager = LinearLayoutManager(this)

        articleAdapter = ArticlesAdapter { article -> openArticleDetailedView(article)}

        layoutManager.orientation = LinearLayoutManager.VERTICAL;
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = articleAdapter
    }

    private fun initViewModel() {
        viewModel = MainViewModel(this)
        viewModel.articlesLiveData.observe(this, Observer {
            articleAdapter.submitList(it as MutableList<Article>)
        })
    }

    private fun fetchNewsList() {
        // TODO: Implement dynamic handling of keywords
        viewModel.getNewsListFor(listOf("Google", "Apple", "Facebook"))
    }

    private fun openArticleDetailedView(article: Article) {
        Intent(this, DetailedArticleActivity::class.java).apply {
            putExtra("title", article.title)
            putExtra("content", article.content)
            putExtra("urlToImage", article.urlToImage)
        }.also {
            startActivity(it)
        }
    }
}
