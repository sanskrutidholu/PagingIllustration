package com.example.mypaginationlibrary.pagingSample1.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mypaginationlibrary.R
import com.example.mypaginationlibrary.pagingSample1.retrofit.News
import com.squareup.picasso.Picasso

class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(news: News) {
        val newsName = itemView.findViewById<TextView>(R.id.tv_newsName)
        newsName.text = news.title

        val newsImage = itemView.findViewById<ImageView>(R.id.iv_news)
        if (news.image.isNotEmpty())
            Picasso.get().load(news.image).into(newsImage)
    }

    companion object {
        fun create(parent: ViewGroup): NewsViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_news, parent, false)
            return NewsViewHolder(view)
        }
    }
}