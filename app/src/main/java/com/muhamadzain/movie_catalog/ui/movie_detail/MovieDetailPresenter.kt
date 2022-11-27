package com.muhamadzain.movie_catalog.ui.movie_detail

import android.util.Log
import com.muhamadzain.movie_catalog.network.ServiceApi
import com.muhamadzain.movie_catalog.utils.Constant
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieDetailPresenter(
    private val view: MovieDetailView.View
) : MovieDetailView.Presenter {

    private val mCompositeDisposable : CompositeDisposable?

    init {
        this.mCompositeDisposable = CompositeDisposable()
    }

    override fun sendDetailMovie(imdbId: String) {
        view.showLoading()
        val disposable = ServiceApi.endpoint.getDetailMovie(Constant.API_KEY, imdbId, "full")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe( {
                if (it.response != "False") {
                    view.onSuccessRetrieve(it)
                } else {
                    view.onReject(it)
                }
                view.hideLoading()
            },{
                view.hideLoading()
                Log.e("error", it.toString())
        })
        mCompositeDisposable!!.add(disposable)
    }

    override fun subcribe() {}

    override fun unubscribe() {
        mCompositeDisposable!!.clear()
    }

}