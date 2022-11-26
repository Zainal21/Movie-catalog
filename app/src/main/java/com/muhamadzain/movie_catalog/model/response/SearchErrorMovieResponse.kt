package com.muhamadzain.movie_catalog.model.response

import com.google.gson.annotations.SerializedName

data class SearchErrorMovieResponse(

	@field:SerializedName("Response")
	val response: String? = null,

	@field:SerializedName("Error")
	val error: String? = null
)
