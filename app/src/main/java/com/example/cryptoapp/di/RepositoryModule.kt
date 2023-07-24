package com.example.cryptocurrency.di

import com.example.cryptocurrency.data.repository.CryptoCurrencyRepositoryImpl
import com.example.cryptocurrency.domain.datasource.remote.CryptoCurrencyRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideCryptoCurrencyRepository(cryptoCurrencyRemoteDataSource: CryptoCurrencyRemoteDataSource): CryptoCurrencyRepositoryImpl =
        CryptoCurrencyRepositoryImpl(cryptoCurrencyRemoteDataSource)
}