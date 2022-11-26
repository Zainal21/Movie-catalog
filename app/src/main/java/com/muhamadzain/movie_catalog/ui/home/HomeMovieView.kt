package com.muhamadzain.movie_catalog.ui.home

import com.muhamadzain.movie_catalog.base.BasePresenter
import com.muhamadzain.movie_catalog.base.BaseView
import com.muhamadzain.movie_catalog.model.response.SearchMovieResponse

interface HomeMovieView {
    interface View : BaseView{
        fun onSuccessRetrieve(response : SearchMovieResponse)
        fun onReject(response: SearchMovieResponse)
    }

    interface Presenter : HomeMovieView, BasePresenter{
        fun sendSearchMovie(keyword : String)
    }
}