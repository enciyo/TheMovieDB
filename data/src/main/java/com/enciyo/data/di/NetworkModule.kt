package com.enciyo.data.di

import com.enciyo.data.*
import com.enciyo.data.interceptor.DefaultConfigInterceptor
import com.enciyo.data.adapters.JsonAdapterDouble
import com.enciyo.data.adapters.JsonAdapterInt
import com.enciyo.data.adapters.JsonAdapterLong
import com.enciyo.data.adapters.JsonAdapterString
import com.enciyo.data.adapters.call.EitherCallAdapterFactory
import com.enciyo.data.remote.NetworkService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideCallAdapterFactory(): CallAdapter.Factory =
        EitherCallAdapterFactory()

    @Singleton
    @Provides
    fun provideConverterFactory(moshi: Moshi): Converter.Factory =
        MoshiConverterFactory.create(moshi)

    @Singleton
    @Provides
    fun provideOkhttp(configInterceptor: DefaultConfigInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(configInterceptor)
            .build()

    @Singleton
    @Provides
    fun provideMoshi(
        stringAdapter: JsonAdapterString,
        doubleAdapter: JsonAdapterDouble,
        longAdapter: JsonAdapterLong,
        intAdapter: JsonAdapterInt
    ): Moshi =
        Moshi.Builder()
            .add(Int::class.java, intAdapter)
            .add(String::class.java, stringAdapter)
            .add(Long::class.java, longAdapter)
            .add(Double::class.java, doubleAdapter)
            .add(KotlinJsonAdapterFactory())
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(
        client: OkHttpClient,
        converterFactory: Converter.Factory,
        callAdapterFactory: CallAdapter.Factory
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .client(client)
            .addConverterFactory(converterFactory)
            .addCallAdapterFactory(callAdapterFactory)
            .build()

    @Singleton
    @Provides
    fun provideService(retrofit: Retrofit): NetworkService =
        retrofit
            .create(NetworkService::class.java)

}