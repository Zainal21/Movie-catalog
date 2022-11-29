package com.muhamadzain.movie_catalog.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.card.MaterialCardView
import com.muhamadzain.movie_catalog.R
import com.muhamadzain.movie_catalog.base.BaseActivity
import com.muhamadzain.movie_catalog.model.response.SearchMovieResponse

@Suppress("DEPRECATION", "UNCHECKED_CAST")
class HomeMovieActivity : BaseActivity(), HomeMovieView.View {

    override fun getLayoutResource(): Int = R.layout.activity_main

    private var doubleBackToExitPressedOnce = false

    private lateinit var btnSearchMovie : MaterialCardView
    private lateinit var inputFormSearchKeyword : EditText
    private lateinit var shimmerLayoutContainerItem : ShimmerFrameLayout
    private lateinit var recylerViewMovieItem : RecyclerView
    private lateinit var notFoundContainer : LinearLayout
    private lateinit var presenter: HomeMoviePresenter
    private lateinit var cardContainerResultMovieList : CardView
    private lateinit var cardContainerShimmer : CardView
    private lateinit var txtMessageNotFound : TextView

    private fun initComponents(){
        btnSearchMovie = findViewById(R.id.btn_search_movie)
        inputFormSearchKeyword = findViewById(R.id.form_input_keyword_search)
        shimmerLayoutContainerItem = findViewById(R.id.container_shimmer_car_layout)
        recylerViewMovieItem = findViewById(R.id.rvMovieContent)
        notFoundContainer = findViewById(R.id.not_found_container)
        cardContainerResultMovieList = findViewById(R.id.card_container_result_movie_list)
        cardContainerShimmer = findViewById(R.id.card_container_shimmer)
        txtMessageNotFound = findViewById(R.id.txt_message_not_found)
    }

    private fun initEventListener(){
        btnSearchMovie.setOnClickListener {
            clearRecylerView()
            val keyword = inputFormSearchKeyword.text.toString()
            if(keyword.isEmpty()){
                Toast.makeText(this, "Pencarian Kata kunci film belum diisikan", Toast.LENGTH_SHORT).show()
            }else{
                presenter.sendSearchMovie(keyword)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_movie)
        presenter = HomeMoviePresenter(this)
        initComponents()
        initEventListener()
        shimmerLayoutContainerItem.visibility = View.GONE
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if(doubleBackToExitPressedOnce){
            super.onBackPressed()
            return
        }
        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Tekan Kembali Sekali lagi untuk keluar", Toast.LENGTH_SHORT).show()
        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }

    @SuppressLint("LongLogTag", "SetTextI18n")
    override fun onSuccessRetrieve(response: SearchMovieResponse) {
        clearRecylerView()
        val status = response.response
        notFoundContainer.visibility = View.GONE
        shimmerLayoutContainerItem.visibility = View.GONE
        cardContainerResultMovieList.visibility = View.GONE
        val inputManager: InputMethodManager =getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(
            currentFocus?.windowToken,
            InputMethodManager.SHOW_FORCED
        )
        if(status.toString() == "True"){
            cardContainerShimmer.visibility = View.VISIBLE
            cardContainerResultMovieList.visibility = View.GONE
            recylerViewMovieItem.visibility = View.VISIBLE
            recylerViewMovieItem.layoutManager = LinearLayoutManager(this)
            val moviewItems = HomeMovieAdapter(this,
                response.search as ArrayList<SearchMovieResponse.SearchItem>
            )
            recylerViewMovieItem.adapter = moviewItems
        }else{
            txtMessageNotFound.text = response.error + "\uD83D\uDE22"
            cardContainerShimmer.visibility = View.GONE
            cardContainerResultMovieList.visibility = View.VISIBLE
            notFoundContainer.visibility = View.VISIBLE
        }
    }

    fun clearRecylerView(){
        cardContainerShimmer.visibility = View.VISIBLE
        shimmerLayoutContainerItem.visibility = View.VISIBLE
        cardContainerResultMovieList.visibility = View.VISIBLE
    }

    override fun onReject(response: SearchMovieResponse) {
        cardContainerResultMovieList.visibility = View.VISIBLE
        notFoundContainer.visibility = View.VISIBLE
        cardContainerShimmer.visibility = View.GONE
        shimmerLayoutContainerItem.visibility = View.GONE
    }

    override fun showLoading() {
        shimmerLayoutContainerItem.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        shimmerLayoutContainerItem.visibility = View.GONE
    }
}