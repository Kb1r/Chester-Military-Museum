package com.example.chestermilitarymuseum.adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chestermilitarymuseum.R
import com.example.chestermilitarymuseum.models.Post

class NewsAdapter(private val posts: List<Post>) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.newsTitle)
        val image: ImageView = view.findViewById(R.id.newsImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val post = posts[position]
        holder.title.text = android.text.Html.fromHtml(post.title.rendered).toString()

        Glide.with(holder.itemView.context)
            .load(post.jetpack_featured_media_url)
            .into(holder.image)

        holder.itemView.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.link))
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount() = posts.size
}
