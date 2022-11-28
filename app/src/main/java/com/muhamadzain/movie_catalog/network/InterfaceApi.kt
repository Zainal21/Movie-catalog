package com.muhamadzain.movie_catalog.network

import com.muhamadzain.movie_catalog.model.response.SearchDetailMovieResponse
import com.muhamadzain.movie_catalog.model.response.SearchMovieResponse
import com.muhamadzain.movie_catalog.utils.Constant
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface InterfaceApi {
    @GET("/")
    fun searchMovie(
        @Query("apikey") apikey : String? = Constant.API_KEY,
        @Query("s") s: String? = ""
    ) : Observable<SearchMovieResponse>

    @GET("/")
    fun getDetailMovie(
        @Query("apikey") apikey : String? = Constant.API_KEY,
        @Query("i") t: String? = "",
        @Query("plot") plot: String? = "full"
    ) : Observable<SearchDetailMovieResponse>
}