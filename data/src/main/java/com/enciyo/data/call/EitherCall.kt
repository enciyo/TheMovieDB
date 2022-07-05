package com.enciyo.data.call

import arrow.core.Either
import com.enciyo.shared.ErrorEntity
import com.enciyo.shared.HttpError
import com.enciyo.shared.NetworkError
import com.enciyo.shared.UnknownError
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.lang.reflect.Type

//@see https://proandroiddev.com/retrofit-calladapter-for-either-type-2145781e1c20
class EitherCall<R>(
    private val delegate: Call<R>,
    private val successType: Type
) : Call<Either<ErrorEntity, R>> {

    override fun clone(): Call<Either<ErrorEntity, R>> = EitherCall(delegate.clone(), successType)

    @Deprecated(
        "",
        ReplaceWith("throw UnsupportedOperationException(\"Either doesn't call execute\")")
    )

    override fun execute(): Response<Either<ErrorEntity, R>> =
        throw UnsupportedOperationException("Either doesn't call execute")

    override fun isExecuted(): Boolean = delegate.isExecuted

    override fun cancel() = delegate.cancel()

    override fun isCanceled(): Boolean = delegate.isCanceled

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()

    override fun enqueue(callback: Callback<Either<ErrorEntity, R>>) =
        delegate.enqueue(
            object : Callback<R> {

                override fun onResponse(
                    call: Call<R>,
                    response: Response<R>
                ) {
                    callback.onResponse(this@EitherCall, Response.success(response.toEither()))
                }

                private fun Response<R>.toEither(): Either<ErrorEntity, R> {
                    // Http error response (4xx - 5xx)
                    if (!isSuccessful) {
                        val errorBody = errorBody()?.string() ?: ""
                        return Either.Left(HttpError(code(), errorBody))
                    }

                    // Http success response with body
                    body()?.let { body ->
                        return Either.Right(body)
                    }
                    // if we defined Unit as success type it means we expected no response body
                    // e.g. in case of 204 No Content
                    return if (successType == Unit::class.java) {
                        @Suppress("UNCHECKED_CAST")
                        Either.Right(Unit) as Either<ErrorEntity, R>
                    } else {
                        @Suppress("UNCHECKED_CAST")
                        Either.Left(UnknownError("Response body was null")) as Either<ErrorEntity, R>
                    }
                }

                override fun onFailure(call: Call<R>, throwable: Throwable) {
                    val error = when (throwable) {
                        is IOException -> NetworkError(throwable)
                        else -> UnknownError(throwable)
                    }
                    callback.onResponse(this@EitherCall, Response.success(Either.Left(error)))
                }
            }
        )
}

