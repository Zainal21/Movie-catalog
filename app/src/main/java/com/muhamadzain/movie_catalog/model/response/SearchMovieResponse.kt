package com.muhamadzain.movie_catalog.model.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class SearchMovieResponse(

	@field:SerializedName("Response")
	val response: String? = null,

	@field:SerializedName("totalResults")
	val totalResults: String? = null,

	@field:SerializedName("Search")
	val search: List<SearchItem?>? = null,

	@field:SerializedName("Error")
	val error: String = ""
){
	@Keep
	data class SearchItem(

		@field:SerializedName("Type")
		val type: String? = null,

		@field:SerializedName("Year")
		val year: String? = null,

		@field:SerializedName("imdbID")
		val imdbID: String? = null,

		@field:SerializedName("Poster")
		val poster: String? = null,

		@field:SerializedName("Title")
		val title: String? = null
	)
}

