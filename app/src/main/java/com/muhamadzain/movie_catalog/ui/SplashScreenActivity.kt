package com.muhamadzain.movie_catalog.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.muhamadzain.movie_catalog.R
import com.muhamadzain.movie_catalog.base.BaseActivity
import com.muhamadzain.movie_catalog.ui.home.HomeMovieActivity

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : BaseActivity() {

    override fun getLayoutResource(): Int = R.layout.activity_main
    private val SPLASH_TIME_OUT = 2000L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Handler().postDelayed(
            {
                val i = Intent(this@SplashScreenActivity, HomeMovieActivity::class.java)
                startActivity(i)
                finish()
            }, SPLASH_TIME_OUT
        )
    }
}