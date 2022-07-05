package com.enciyo.domain

import com.enciyo.domain.model.PersonDTO
import com.enciyo.shared.EitherResult
import javax.inject.Inject

class SearchPersonUseCase @Inject constructor(
    private val repository: Repository
) : FlowUseCase<SearchPersonUseCase.Request, List<PersonDTO>>() {

    override suspend fun execute(input: Request): EitherResult<List<PersonDTO>> =
        repository.searchPerson(input.query)


    data class Request(
        val query: String
    )
}