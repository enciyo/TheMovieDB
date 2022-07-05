package com.enciyo.shared

import arrow.core.Either
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach


sealed class ErrorEntity
data class HttpError(val code: Int, val body: String) : ErrorEntity()
data class NetworkError(val throwable: Throwable) : ErrorEntity()
data class UnknownError(val throwable: Throwable) : ErrorEntity()



typealias EitherResult<T> = Either<ErrorEntity, T>


inline fun <T, O> EitherResult<T>.mapIfSuccess(crossinline consumer: (T) -> O): EitherResult<O> =
    when (this) {
        is Either.Left -> this
        is Either.Right -> Either.Right(consumer.invoke(this.value))
    }


inline fun <T, O> Flow<EitherResult<T>>.mapIfSuccess(crossinline consumer: (T) -> O): Flow<EitherResult<O>> =
    this.map { it.mapIfSuccess(consumer) }
        .flowOn(Dispatchers.Default)


inline fun <T> Flow<EitherResult<T>>.onEachIfSuccess(crossinline consumer: (T) -> Unit): Flow<EitherResult<T>> =
    this
        .onEach {
            when(it){
                is Either.Left -> Unit
                is Either.Right -> consumer.invoke(it.value)
            }
        }