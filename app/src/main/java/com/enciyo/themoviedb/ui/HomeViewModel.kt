package com.enciyo.themoviedb.ui

import androidx.lifecycle.MutableLiveData
import com.enciyo.domain.PopularMoviesGetUseCase
import com.enciyo.domain.SearchMovieUseCase
import com.enciyo.domain.SearchPersonUseCase
import com.enciyo.domain.SearchTVUseCase
import com.enciyo.shared.mapIfSuccess
import com.enciyo.themoviedb.ext.asLiveData
import com.enciyo.themoviedb.mapper.MovieDTOToHomeAdapterModelMapper
import com.enciyo.themoviedb.mapper.PersonDTOToHomeAdapterModelMapper
import com.enciyo.themoviedb.mapper.TVDTOToHomeAdapterModelMapper
import com.enciyo.themoviedb.ui.HomeViewModel.MediaType.*
import com.enciyo.themoviedb.ui.adapter.HomeAdapter
import com.enciyo.themoviedb.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.IllegalStateException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val popularMoviesGetUseCase: PopularMoviesGetUseCase,
    private val searchMovieUseCase: SearchMovieUseCase,
    private val searchPersonUseCase: SearchPersonUseCase,
    private val searchTVUseCase: SearchTVUseCase,
    private val movieDTOToHomeAdapterModelMapper: MovieDTOToHomeAdapterModelMapper,
    private val personDTOToHomeAdapterModelMapper: PersonDTOToHomeAdapterModelMapper,
    private val tvDTOToHomeAdapterModelMapper: TVDTOToHomeAdapterModelMapper
) : BaseViewModel() {


    private val _state = MutableLiveData(HomeViewState())
    val state get() = _state.asLiveData()


    init {
        fetchPopularMovies()
    }


    private fun fetchPopularMovies() {
        popularMoviesGetUseCase.invoke(Unit)
            .mapIfSuccess { it.map(movieDTOToHomeAdapterModelMapper::mapTo) }
            .checkIsEmpty()
            .get(onSuccess = {
                _state.value = currentState().copy(
                    items = it,
                    mediaType = None
                )
            })
    }


    fun search(query: String, type: MediaType) {
        if (query.isEmpty()){
            cancelSearch()
            return
        }

        when (type) {
            None -> cancelSearch()
            TV -> searchTV(query)
            Movie -> searchMovie(query)
            Person -> searchPerson(query)
        }
    }


    fun cancelSearch() {
        fetchPopularMovies()
    }

    private fun searchMovie(query: String) {
        val req = SearchMovieUseCase.Request(query)
        searchMovieUseCase.invoke(req)
            .mapIfSuccess { it.map(movieDTOToHomeAdapterModelMapper::mapTo) }
            .checkIsEmpty()
            .get(onSuccess = {
                _state.value = currentState().copy(
                    items = it,
                    mediaType = Movie
                )
            })
    }

    private fun searchPerson(query: String) {
        val req = SearchPersonUseCase.Request(query)
        searchPersonUseCase.invoke(req)
            .mapIfSuccess { it.map(personDTOToHomeAdapterModelMapper::mapTo) }
            .checkIsEmpty()
            .get(onSuccess = {
                _state.value = currentState().copy(
                    items = it,
                    mediaType = Person
                )
            })
    }

    private fun searchTV(query: String) {
        val req = SearchTVUseCase.Request(query)
        searchTVUseCase.invoke(req)
            .mapIfSuccess { it.map(tvDTOToHomeAdapterModelMapper::mapTo) }
            .checkIsEmpty()
            .get(onSuccess = {
                _state.value = currentState().copy(
                    items = it,
                    mediaType = TV
                )
            })
    }


    private fun currentState() =
        _state.value ?: throw IllegalStateException("Not found default state")


    data class HomeViewState(
        val items: List<HomeAdapter.HomeAdapterModel> = listOf(),
        val mediaType: MediaType = None
    ) {
        val isTabVisible
            get() = mediaType != None
    }

    enum class MediaType {
        None,
        TV,
        Movie,
        Person;

        companion object {
            fun findByPosition(position: Int) = when (position) {
                0 -> Movie
                1 -> Person
                2 -> TV
                else -> throw IllegalStateException("$position")
            }
        }
    }


}


