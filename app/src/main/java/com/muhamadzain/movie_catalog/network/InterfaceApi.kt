package com.muhamadzain.movie_catalog.network

import com.muhamadzain.movie_catalog.model.response.SearchMovieResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface InterfaceApi {
    @GET("/")
    fun searchMovie(@Query("s") s: String? = "",) : Observable<SearchMovieResponse>
}