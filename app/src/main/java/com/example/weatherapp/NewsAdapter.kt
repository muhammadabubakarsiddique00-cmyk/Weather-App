package com.example.weatherapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NewsAdapter(
    private val newsList: List<NewsModel>
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    class NewsViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val txtNewsTitle: TextView =
            itemView.findViewById(R.id.txtNewsTitle)

        val txtNewsDescription: TextView =
            itemView.findViewById(R.id.txtNewsDescription)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_news, parent, false)

        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: NewsViewHolder,
        position: Int
    ) {
        val news = newsList[position]

        holder.txtNewsTitle.text = news.title
        holder.txtNewsDescription.text = news.description
    }

    override fun getItemCount(): Int {
        return newsList.size
    }
}