package com.example.cryptoapp.di

import com.example.cryptoapp.data.repository.CryptoCurrencyRepositoryImpl
import com.example.cryptoapp.data.repository.FirebaseRepositoryImpl
import com.example.cryptoapp.domain.datasource.remote.CryptoCurrencyRemoteDataSource
import com.example.cryptoapp.domain.datasource.remote.FirebaseRemoteDataSource
import com.example.cryptoapp.domain.repository.CryptoCurrencyRepository
import com.example.cryptoapp.domain.repository.FirebaseRepository
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
    fun provideCryptoCurrencyRepository(cryptoCurrencyRemoteDataSource: CryptoCurrencyRemoteDataSource): CryptoCurrencyRepository {
       return CryptoCurrencyRepositoryImpl(cryptoCurrencyRemoteDataSource)
    }

    @Singleton
    @Provides
    fun provideFirebaseRepository(firebaseRemoteDataSource: FirebaseRemoteDataSource): FirebaseRepository =
        FirebaseRepositoryImpl(firebaseRemoteDataSource)
}