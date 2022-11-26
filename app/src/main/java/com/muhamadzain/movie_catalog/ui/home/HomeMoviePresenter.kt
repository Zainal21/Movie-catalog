package com.muhamadzain.movie_catalog.ui.home

import android.util.Log
import com.muhamadzain.movie_catalog.network.ServiceApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class HomeMoviePresenter(
    private val view : HomeMovieView.View
) : HomeMovieView.Presenter {
    private val mCompositeDisposable: CompositeDisposable?

    init {
        this.mCompositeDisposable = CompositeDisposable()
    }

    override fun sendSearchMovie(keyword: String) {
        view.showLoading()
        val disposable = ServiceApi.endpoint.searchMovie(keyword)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if(it.response != "False"){
                    view.onSuccessRetrieve(it)
                }else{
                    view.onReject(it)
                }
                view.hideLoading()
            },{
                view.hideLoading()
                Log.e("error", it.toString())
            })

        mCompositeDisposable!!.add(disposable)
    }

    override fun subcribe() {

    }

    override fun unubscribe() {
        mCompositeDisposable!!.clear()
    }

}