package com.example.cryptocurrency.di

import com.example.cryptocurrency.api.CryptoCurrencyService
import com.example.cryptocurrency.data.datasource.remote.CryptoCurrencyRemoteDataSourceImpl
import dagger.Provides
import javax.inject.Singleton

object DataSourceModule {
    @Singleton
    @Provides
    fun provideCryptoCurrencyRemoteDataSource(cryptoCurrencyService: CryptoCurrencyService): CryptoCurrencyRemoteDataSourceImpl {
        return CryptoCurrencyRemoteDataSourceImpl(cryptoCurrencyService)
    }

}