package com.muhamadzain.movie_catalog.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.google.android.material.card.MaterialCardView
import com.muhamadzain.movie_catalog.R
import com.muhamadzain.movie_catalog.model.response.SearchMovieResponse
import com.muhamadzain.movie_catalog.ui.movie_detail.MovieDetailActivity

class HomeMovieAdapter(
    private val context: Context,
    private val movieItemsList : ArrayList<SearchMovieResponse.SearchItem>) : RecyclerView.Adapter<HomeMovieAdapter.MyViewHolder>()
{
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.movie_title_content)
        var date: TextView = view.findViewById(R.id.date_published)
        var image_thumbnail = view.findViewById<ImageView>(R.id.image_thumbnail)
        var container_movie_card = view.findViewById<MaterialCardView>(R.id.container_movie_card)
        var movie_short_description = view.findViewById<TextView>(R.id.movie_short_description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val viewItem = LayoutInflater.from(parent.context).inflate(R.layout.item_card_movie, parent, false)
        return MyViewHolder(viewItem)
   }

    @SuppressLint("ResourceType", "SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = movieItemsList[position]
        holder.title.text = data.title.toString()
        holder.date.text = data.year.toString()
        holder.movie_short_description.text = "Type : " + data.type.toString()
        val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
        Glide.with(holder.image_thumbnail)
            .load(data.poster.toString())
            .error(R.layout.shimmer_item_card_movie)
            .centerCrop()
            .transition(DrawableTransitionOptions.withCrossFade(factory))
            .into(holder.image_thumbnail)
        holder.container_movie_card.setOnClickListener {
            val _intent = Intent(holder.itemView.context, MovieDetailActivity::class.java)
            _intent.putExtra("intent_ImdbID", data.imdbID)
            holder.itemView.context.startActivity(_intent)
        }
    }

    override fun getItemCount(): Int{
        return movieItemsList.size
    }
}