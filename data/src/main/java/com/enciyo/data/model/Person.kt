package com.enciyo.data.model

import com.squareup.moshi.Json

data class Person(
    @Json(name = "profile_path")
    val profilePath: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "popularity")
    val popularity: Float,
)


