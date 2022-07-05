package com.enciyo.data.mapper

import com.enciyo.data.BuildConfig
import com.enciyo.data.model.TV
import com.enciyo.domain.model.TVDTO
import com.enciyo.shared.Mapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TVToTVDTOMapper @Inject constructor() : Mapper<TV, TVDTO> {
    override fun mapTo(input: TV): TVDTO =
        TVDTO(
            img = BuildConfig.API_IMAGE_PREF + input.posterPath,
            name = input.name,
            vote = input.voteAverage
        )
}