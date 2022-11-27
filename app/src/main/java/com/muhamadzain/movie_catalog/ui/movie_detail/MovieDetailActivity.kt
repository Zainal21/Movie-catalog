package com.muhamadzain.movie_catalog.ui.movie_detail

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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
            movie_plot.text = response.plot
            movie_timestamp.text = "Release : " + response.released +  " | " + response.runtime + "\n"  + "Language : " + response.language
            movie_title.text = response.title
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
