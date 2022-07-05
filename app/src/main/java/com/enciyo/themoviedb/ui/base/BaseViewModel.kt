package com.enciyo.themoviedb.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.enciyo.shared.*
import com.enciyo.themoviedb.ext.asLiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart


abstract class BaseViewModel : ViewModel() {

    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState get() = _loadingState.asLiveData()

    private val _infoState = MutableLiveData<String>()
    val infoState get() = _infoState.asLiveData()


    fun <A> Flow<EitherResult<List<A>>>.checkIsEmpty() = this
        .onEachIfSuccess {
            if (it.isEmpty())
                _infoState.value = "List is Empty"
        }


    fun <A> Flow<EitherResult<A>>.get(
        onError: (ErrorEntity) -> Unit = {},
        config: Config = Config(),
        onSuccess: (A) -> Unit,
    ) {
        this
            .onEach {
                it.handleAction(onError, config, onSuccess)
            }
            .onStart {
                if (config.isShowLoading)
                    _loadingState.value = true
                _infoState.value = ""
            }
            .onEach {
                if (config.isShowLoading)
                    _loadingState.value = false
            }
            .launchIn(viewModelScope)
    }


    private fun <A> EitherResult<A>.handleAction(
        onError: (ErrorEntity) -> Unit = {},
        config: Config = Config(),
        onSuccess: (A) -> Unit,
    ) {
        when (this) {
            is Either.Left -> {
                onError.invoke(this.value)
                handleError(config, this.value)
            }
            is Either.Right -> {
                onSuccess.invoke(this.value)
            }
        }
    }

    private fun handleError(config: Config, errorEntity: ErrorEntity) {
        when (errorEntity) {
            is HttpError -> {
                if (config.isShowError)
                    _infoState.value = "Http Error ${errorEntity.body.toString()}"
            }
            is NetworkError -> {
                if (config.isShowError)
                    _infoState.value = "Network Error ${errorEntity.throwable.message}"
            }
            is UnknownError -> {
                if (config.isShowError)
                    _infoState.value = "Unknown Api Error ${errorEntity.throwable.message}"
            }
        }
    }


    data class Config(
        val isShowError: Boolean = true,
        val isShowLoading: Boolean = true
    )


}