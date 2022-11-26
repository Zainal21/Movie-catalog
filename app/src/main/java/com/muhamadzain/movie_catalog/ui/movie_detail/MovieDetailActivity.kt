package com.muhamadzain.movie_catalog.ui.movie_detail

import android.os.Bundle
import com.muhamadzain.movie_catalog.R
import com.muhamadzain.movie_catalog.base.BaseActivity

class MovieDetailActivity : BaseActivity() {
    override fun getLayoutResource(): Int = R.layout.activity_movie_detail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
    }
}