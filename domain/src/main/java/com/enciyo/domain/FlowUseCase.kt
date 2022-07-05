package com.enciyo.domain

import arrow.core.Either
import com.enciyo.shared.EitherResult
import com.enciyo.shared.UnknownError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow


abstract class FlowUseCase<in I, out O> {

    operator fun invoke(input: I): Flow<EitherResult<O>> = flow {
        kotlinx.coroutines.delay(1000)
        emit(execute(input))
    }
        .catch { t ->
            emit(Either.Left(UnknownError(t)))
        }

    protected abstract suspend fun execute(input: I): EitherResult<O>

}
