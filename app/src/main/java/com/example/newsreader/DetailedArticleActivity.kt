package com.example.newsreader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.newsreader.models.MainViewModel

class DetailedArticleActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_article)

        viewModel = MainViewModel(this)

        val image: ImageView = findViewById(R.id.detailedImageView)
        val title: TextView = findViewById(R.id.detailedViewTitle)
        val content: TextView = findViewById(R.id.detailedViewContent)

        intent.extras?.run {
            getString("title")?.let {
                title.text = it
            }

            getString("content")?.let {
                content.text = it
            }

            getString("urlToImage")?.let {
                Glide.with(this@DetailedArticleActivity)
                    .load(it)
                    .into(image)
            }
        }
    }

    fun initNavigation() {
        // TODO: Setup proper handling for backing out of detailed view
    }
}