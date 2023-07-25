package com.example.cryptoapp.di

import com.example.cryptoapp.data.datasource.remote.CryptoCurrencyRemoteDataSourceImpl
import com.example.cryptoapp.data.datasource.remote.FirebaseRemoteDataSourceImpl
import com.example.cryptoapp.domain.datasource.remote.CryptoCurrencyRemoteDataSource
import com.example.cryptoapp.domain.datasource.remote.FirebaseRemoteDataSource
import com.example.cryptoapp.api.CryptoCurrencyService
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Singleton
    @Provides
    fun provideCryptoCurrencyRemoteDataSource(cryptoCurrencyService: CryptoCurrencyService): CryptoCurrencyRemoteDataSource =
        CryptoCurrencyRemoteDataSourceImpl(cryptoCurrencyService)

    @Singleton
    @Provides
    fun provideFirebaseRemoteDataSource(auth: FirebaseAuth): FirebaseRemoteDataSource =
        FirebaseRemoteDataSourceImpl(auth)
}