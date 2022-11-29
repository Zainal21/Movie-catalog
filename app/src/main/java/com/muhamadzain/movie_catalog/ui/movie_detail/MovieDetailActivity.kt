package com.muhamadzain.movie_catalog.ui.movie_detail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.muhamadzain.movie_catalog.R
import com.muhamadzain.movie_catalog.base.BaseActivity
import com.muhamadzain.movie_catalog.model.response.SearchDetailMovieResponse
import com.muhamadzain.movie_catalog.ui.home.HomeMovieActivity

class MovieDetailActivity : BaseActivity(), MovieDetailView.View{

    private lateinit var presenter: MovieDetailPresenter
    private lateinit var movie_title : TextView
    private lateinit var movie_timestamp : TextView
    private lateinit var movie_thumbnail : ImageView
    private lateinit var movie_plot : TextView
    private lateinit var btn_back : ImageView

    private fun initComponents(){
        btn_back = findViewById(R.id.btn_back)
        movie_title = findViewById(R.id.movie_title)
        movie_thumbnail = findViewById(R.id.movie_thumbnail)
        movie_timestamp = findViewById(R.id.movie_timestamp)
        movie_plot = findViewById(R.id.movie_plot)
    }

    private fun initEventListener(){
        btn_back.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                finish()
            }
        })
    }

    override fun getLayoutResource(): Int = R.layout.activity_movie_detail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = MovieDetailPresenter(this)
        val intent_imDBId = intent.getStringExtra("intent_ImdbID")
        setContentView(R.layout.activity_movie_detail)
        initComponents()
        initEventListener()
        if (intent_imDBId != null) {
            presenter.sendDetailMovie(intent_imDBId)
        }
    }

    @SuppressLint("LongLogTag", "SetTextI18n", "ResourceType")
    override fun onSuccessRetrieve(response: SearchDetailMovieResponse) {
        val status = response.response
        if(status == "True"){
            movie_title.text = response.title
            movie_timestamp.text =
                Html.fromHtml(
                    "Release      : <strong>" + response.released +  "</strong> | <strong>" + response.runtime + "</strong>"+
                    "<br>"  + "Language   : <strong>" + response.language +"</strong>"+
                    "<br>"  + "Genre      : <strong>" + response.genre +"</strong>"+
                    "<br>"  + "Country    : <strong>" + response.country +"</strong>"+
                    "<br>"  + "Awards     : <strong>" + response.awards +"</strong>"+
                    "<br>"  + "Actors     : <strong>" +response.actors +"</strong>"+
                    "<br>"  + "Writer     : <strong>"+  response.writer +"</strong>"+
                    "<br>"  + "Director   : <strong>" + response.director +"</strong>"+
                    "<br>"  + "Writer     : <strong>" + response.writer +"</strong>"+
                    "<br>"  + "imdbRating : <strong>" + response.imdbRating +"</strong>")
            movie_plot.text = Html.fromHtml("<strong>Plot : </strong>" + response.plot)
            val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
            Glide.with(movie_thumbnail)
                .load(response.poster.toString())
                .error(R.layout.shimmer_item_card_movie)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade(factory))
                .into(movie_thumbnail)
        }
    }

    override fun onReject(response: SearchDetailMovieResponse) {
        Toast.makeText(this, "Data Detail Film Tidak Ditemukan", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, HomeMovieActivity::class.java)
        startActivity(intent)
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }
}
