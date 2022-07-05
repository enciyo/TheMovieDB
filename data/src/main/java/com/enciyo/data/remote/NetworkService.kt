package com.enciyo.data.remote

import com.enciyo.data.model.GenericResponse
import com.enciyo.data.model.Movie
import com.enciyo.data.model.Person
import com.enciyo.data.model.TV
import com.enciyo.shared.EitherResult
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {

    @GET("movie/popular")
    suspend fun fetchMoviesPopular(): EitherResult<GenericResponse<Movie>>

    @GET("search/movie")
    suspend fun searchMovie(@Query("query") query: String): EitherResult<GenericResponse<Movie>>

    @GET("search/person")
    suspend fun searchPerson(@Query("query") query: String): EitherResult<GenericResponse<Person>>

    @GET("search/tv")
    suspend fun searchTV(@Query("query") query: String): EitherResult<GenericResponse<TV>>


}