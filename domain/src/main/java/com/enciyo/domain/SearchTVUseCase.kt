package com.enciyo.domain

import com.enciyo.domain.model.TVDTO
import com.enciyo.shared.EitherResult
import javax.inject.Inject

class SearchTVUseCase @Inject constructor(
    private val repository: Repository
) : FlowUseCase<SearchTVUseCase.Request, List<TVDTO>>() {

    override suspend fun execute(input: Request): EitherResult<List<TVDTO>> =
        repository.searchTV(input.query)


    data class Request(
        val query: String
    )
}