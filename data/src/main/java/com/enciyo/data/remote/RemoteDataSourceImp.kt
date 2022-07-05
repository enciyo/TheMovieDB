package com.enciyo.data.remote

import com.enciyo.data.RemoteDataSource
import com.enciyo.data.mapper.MoviePopularToMovieDTOMapper
import com.enciyo.data.mapper.PersonToPersonDTOMapper
import com.enciyo.data.mapper.TVToTVDTOMapper
import com.enciyo.domain.model.MovieDTO
import com.enciyo.domain.model.PersonDTO
import com.enciyo.domain.model.TVDTO
import com.enciyo.shared.EitherResult
import com.enciyo.shared.mapIfSuccess
import javax.inject.Inject

class RemoteDataSourceImp @Inject constructor(
    private val networkService: NetworkService,
    private val moviePopularToMovieDTOMapper: MoviePopularToMovieDTOMapper,
    private val personToPersonDTOMapper: PersonToPersonDTOMapper,
    private val tvToTvDTOMapper: TVToTVDTOMapper
) : RemoteDataSource {
    override suspend fun getMoviesPopular(): EitherResult<List<MovieDTO>> =
        networkService.fetchMoviesPopular()
            .mapIfSuccess {
                it.results.map(moviePopularToMovieDTOMapper::mapTo)
            }

    override suspend fun searchMovie(query: String): EitherResult<List<MovieDTO>> =
        networkService.searchMovie(query)
            .mapIfSuccess {
                it.results.map(moviePopularToMovieDTOMapper::mapTo)
            }


    override suspend fun searchPerson(query: String): EitherResult<List<PersonDTO>> =
        networkService.searchPerson(query)
            .mapIfSuccess {
                it.results.map(personToPersonDTOMapper::mapTo)
            }


    override suspend fun searchTV(query: String): EitherResult<List<TVDTO>> =
        networkService.searchTV(query)
            .mapIfSuccess {
                it.results.map(tvToTvDTOMapper::mapTo)
            }
}