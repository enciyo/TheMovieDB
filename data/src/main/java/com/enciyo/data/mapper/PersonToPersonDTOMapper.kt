package com.enciyo.data.mapper

import com.enciyo.data.BuildConfig
import com.enciyo.data.model.Person
import com.enciyo.domain.model.PersonDTO
import com.enciyo.shared.Mapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PersonToPersonDTOMapper @Inject constructor() : Mapper<Person, PersonDTO> {
    override fun mapTo(input: Person): PersonDTO =
        PersonDTO(
            img = BuildConfig.API_IMAGE_PREF + input.profilePath,
            name = input.name,
            popularity = input.popularity.toString()
        )
}


