package com.enciyo.data.mapper

import com.enciyo.data.BuildConfig
import com.enciyo.data.model.Movie
import com.enciyo.domain.model.MovieDTO
import com.enciyo.shared.Mapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviePopularToMovieDTOMapper @Inject constructor() : Mapper<Movie, MovieDTO> {
    override fun mapTo(input: Movie): MovieDTO =
        MovieDTO(
            img = BuildConfig.API_IMAGE_PREF + input.posterPath,
            name = input.originalTitle,
            description = input.overview
        )
}


