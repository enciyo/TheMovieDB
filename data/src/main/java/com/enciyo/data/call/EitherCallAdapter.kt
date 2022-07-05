package com.enciyo.data.adapters.call

import arrow.core.Either
import com.enciyo.data.call.EitherCall
import com.enciyo.shared.ErrorEntity
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class EitherCallAdapter<R>(
    private val successType: Type
) : CallAdapter<R, Call<Either<ErrorEntity, R>>> {

    override fun adapt(call: Call<R>): Call<Either<ErrorEntity, R>> =
        EitherCall(call, successType)

    override fun responseType(): Type = successType
}