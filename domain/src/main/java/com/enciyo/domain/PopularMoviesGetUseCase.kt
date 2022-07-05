package com.enciyo.domain

import com.enciyo.domain.model.MovieDTO
import com.enciyo.shared.EitherResult
import javax.inject.Inject


class PopularMoviesGetUseCase @Inject constructor(
    private val repository: Repository
) : FlowUseCase<Unit, List<MovieDTO>>() {

    override suspend fun execute(input: Unit): EitherResult<List<MovieDTO>> =
        repository.popularMovies()
}

