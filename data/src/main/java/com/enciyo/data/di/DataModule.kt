package com.enciyo.data.di

import com.enciyo.data.RemoteDataSource
import com.enciyo.data.remote.RemoteDataSourceImp
import com.enciyo.data.RepositoryImp
import com.enciyo.domain.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Singleton
    @Binds
    abstract fun bindRemoteDataSource(remoteDataSourceImp: RemoteDataSourceImp): RemoteDataSource

    @Singleton
    @Binds
    abstract fun bindRepository(repositoryImp: RepositoryImp): Repository

}