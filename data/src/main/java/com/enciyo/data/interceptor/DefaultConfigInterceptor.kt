package com.enciyo.data.interceptor

import com.enciyo.data.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class DefaultConfigInterceptor @Inject constructor() : Interceptor {
    companion object {
        private const val QUERY_API_KEY = "api_key"
        private const val QUERY_LANGUAGE = "language"
        private const val QUERY_PAGE = "page"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        synchronized(this) {
            val originalRequest = chain.request()
            val originalHttpUrl = originalRequest.url()
            val httpUrl = originalHttpUrl
                .newBuilder()
                .addQueryParameter(QUERY_API_KEY, BuildConfig.API_KEY)
                .addQueryParameter(QUERY_LANGUAGE,BuildConfig.API_LANGUAGE)
                .addQueryParameter(QUERY_PAGE,"1")
                .build()

            val newRequest = originalRequest.newBuilder()
                .url(httpUrl)
                .build()
            return chain.proceed(newRequest)
        }
    }
}