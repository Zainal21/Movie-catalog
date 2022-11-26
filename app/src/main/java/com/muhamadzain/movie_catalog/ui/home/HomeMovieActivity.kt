package com.muhamadzain.movie_catalog.ui.home

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.card.MaterialCardView
import com.muhamadzain.movie_catalog.R
import com.muhamadzain.movie_catalog.base.BaseActivity
import com.muhamadzain.movie_catalog.model.response.SearchMovieResponse

@Suppress("DEPRECATION")
class HomeMovieActivity : BaseActivity(), HomeMovieView.View {

    override fun getLayoutResource(): Int = R.layout.activity_main

    private var doubleBackToExitPressedOnce = false

    private lateinit var btnSearchMovie : MaterialCardView
    private lateinit var inputFormSearchKeyword : EditText
    private lateinit var shimmerLayoutContainerItem : ShimmerFrameLayout

    private lateinit var presenter: HomeMoviePresenter

    private fun initComponents(){
        btnSearchMovie = findViewById(R.id.btn_search_movie)
        inputFormSearchKeyword = findViewById(R.id.form_input_keyword_search)
        shimmerLayoutContainerItem = findViewById(R.id.container_shimmer_car_layout)
    }

    private fun initEventListener(){
        btnSearchMovie.setOnClickListener {
            val keyword = inputFormSearchKeyword.text.toString()
            if(keyword == null){
                Toast.makeText(this, "Pencarian Kata kunci film belum diisikan", Toast.LENGTH_SHORT).show();
            }else{
                presenter.sendSearchMovie(keyword)
            }
        }
    }

    private fun getMovieContent(){
        Toast.makeText(this, "This click result movie", Toast.LENGTH_SHORT).show();
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_movie)
        presenter = HomeMoviePresenter(this)
        initComponents()
        initEventListener()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if(doubleBackToExitPressedOnce){
            super.onBackPressed()
            return
        }
        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Tekan Kembali Sekali lagi untuk keluar", Toast.LENGTH_SHORT).show();
        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }

    override fun onSuccessRetrieve(response: SearchMovieResponse) {
    }

    override fun onReject(response: SearchMovieResponse) {
        shimmerLayoutContainerItem.visibility = View.GONE
    }

    override fun showLoading() {
        shimmerLayoutContainerItem.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        shimmerLayoutContainerItem.visibility = View.GONE
    }
}