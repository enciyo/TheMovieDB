package com.enciyo.themoviedb.mapper

import com.enciyo.domain.model.MovieDTO
import com.enciyo.shared.Mapper
import com.enciyo.themoviedb.ui.adapter.HomeAdapter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieDTOToHomeAdapterModelMapper @Inject constructor() :
    Mapper<MovieDTO, HomeAdapter.HomeAdapterModel> {
    override fun mapTo(input: MovieDTO): HomeAdapter.HomeAdapterModel =
        HomeAdapter.HomeAdapterModel(
            img = input.img,
            name = input.name,
            description = input.description
        )
}

