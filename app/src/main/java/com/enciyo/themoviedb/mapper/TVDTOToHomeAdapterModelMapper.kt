package com.enciyo.themoviedb.mapper

import com.enciyo.domain.model.TVDTO
import com.enciyo.shared.Mapper
import com.enciyo.themoviedb.ui.adapter.HomeAdapter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TVDTOToHomeAdapterModelMapper @Inject constructor() :
    Mapper<TVDTO, HomeAdapter.HomeAdapterModel> {
    override fun mapTo(input: TVDTO): HomeAdapter.HomeAdapterModel =
        HomeAdapter.HomeAdapterModel(
            img = input.img,
            name = input.name,
            description = input.vote
        )
}