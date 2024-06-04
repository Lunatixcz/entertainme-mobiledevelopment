package com.mobile.entertainme.response

import com.google.gson.annotations.SerializedName

data class MovieResponse(

	@field:SerializedName("data")
	val data: List<MovieDataItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class MovieDataItem(

	@field:SerializedName("release_year")
	val releaseYear: String? = null,

	@field:SerializedName("poster_url")
	val posterUrl: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("key")
	val key: String? = null
)
