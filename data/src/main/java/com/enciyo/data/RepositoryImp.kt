package com.enciyo.data

import com.enciyo.domain.Repository
import com.enciyo.domain.model.MovieDTO
import com.enciyo.domain.model.PersonDTO
import com.enciyo.domain.model.TVDTO
import com.enciyo.shared.EitherResult
import javax.inject.Inject

class RepositoryImp @Inject constructor(
    private val remoteDataSource: RemoteDataSource
    //LocalDataSource
) : Repository {

    override suspend fun popularMovies() =
        remoteDataSource.getMoviesPopular()

    override suspend fun searchMovie(query: String): EitherResult<List<MovieDTO>> =
        remoteDataSource.searchMovie(query)

    override suspend fun searchPerson(query: String): EitherResult<List<PersonDTO>> =
        remoteDataSource.searchPerson(query)

    override suspend fun searchTV(query: String): EitherResult<List<TVDTO>> =
        remoteDataSource.searchTV(query)

}