package com.enciyo.data.model

import com.squareup.moshi.Json

data class TV(
    @Json(name = "poster_path")
    val posterPath: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "original_name")
    val originalName: String,
    @Json(name = "vote_average")
    val voteAverage: String
)