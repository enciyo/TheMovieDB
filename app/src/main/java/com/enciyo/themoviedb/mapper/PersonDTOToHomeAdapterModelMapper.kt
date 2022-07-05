package com.enciyo.themoviedb.mapper

import com.enciyo.domain.model.PersonDTO
import com.enciyo.shared.Mapper
import com.enciyo.themoviedb.ui.adapter.HomeAdapter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PersonDTOToHomeAdapterModelMapper @Inject constructor() :
    Mapper<PersonDTO, HomeAdapter.HomeAdapterModel> {
    override fun mapTo(input: PersonDTO): HomeAdapter.HomeAdapterModel =
        HomeAdapter.HomeAdapterModel(
            img = input.img,
            name = input.name,
            description = input.popularity
        )
}