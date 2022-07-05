package com.enciyo.data.model

import com.squareup.moshi.Json

data class GenericResponse<A>(
    @Json(name = "page")
    val page: Int,
    @Json(name = "results")
    val results: List<A>,
    @Json(name = "total_pages")
    val totalPages: Int,
    @Json(name = "total_results")
    val totalResults: Int
)