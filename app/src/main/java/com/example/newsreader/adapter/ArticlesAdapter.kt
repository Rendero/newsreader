package com.example.newsreader.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapi.models.Article
import com.example.newsreader.R

class ArticlesAdapter(private val onClick: (Article) -> Unit) :
    ListAdapter<Article, ArticlesAdapter.ArticleViewHolder>(ArticleDiffCallback) {

    class ArticleViewHolder(itemView: View, val onClick: (Article) -> Unit) :
        RecyclerView.ViewHolder(itemView)
    {
        private val titleTextView : TextView = itemView.findViewById(R.id.titleTextView)
        var article: Article? = null

        init {
            itemView.setOnClickListener {
                article?.let {
                    onClick(it)
                }
            }
        }

        fun bind(article: Article) {
            this.article = article
            titleTextView.text = article.title

            Glide.with(itemView)
                .load(article.urlToImage)
                .thumbnail(0.1f)
                .into(itemView.findViewById(R.id.imageView))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.article_list_view, parent, false)

        return ArticleViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

}

object ArticleDiffCallback : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }
}