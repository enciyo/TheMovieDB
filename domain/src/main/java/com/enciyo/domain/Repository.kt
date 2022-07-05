package com.enciyo.domain

import com.enciyo.domain.model.MovieDTO
import com.enciyo.domain.model.PersonDTO
import com.enciyo.domain.model.TVDTO
import com.enciyo.shared.EitherResult

interface Repository {
    suspend fun popularMovies(): EitherResult<List<MovieDTO>>
    suspend fun searchMovie(query:String): EitherResult<List<MovieDTO>>
    suspend fun searchPerson(query:String): EitherResult<List<PersonDTO>>
    suspend fun searchTV(query:String): EitherResult<List<TVDTO>>
}


