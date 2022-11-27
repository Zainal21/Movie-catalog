package com.muhamadzain.movie_catalog.ui.movie_detail

import com.muhamadzain.movie_catalog.base.BasePresenter
import com.muhamadzain.movie_catalog.base.BaseView
import com.muhamadzain.movie_catalog.model.response.SearchDetailMovieResponse

interface MovieDetailView {

    interface View : BaseView{
        fun onSuccessRetrieve(response : SearchDetailMovieResponse)
        fun onReject(response: SearchDetailMovieResponse)
    }

    interface Presenter : MovieDetailView, BasePresenter{
        fun sendDetailMovie(imdbId : String)
    }
}