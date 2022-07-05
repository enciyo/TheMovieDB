package com.enciyo.domain

import com.enciyo.domain.model.MovieDTO
import com.enciyo.shared.EitherResult
import javax.inject.Inject

class SearchMovieUseCase @Inject constructor(
    private val repository: Repository
) : FlowUseCase<SearchMovieUseCase.Request, List<MovieDTO>>() {

    override suspend fun execute(input: Request): EitherResult<List<MovieDTO>> =
        repository.searchMovie(input.query)


    data class Request(
        val query: String
    )
}


