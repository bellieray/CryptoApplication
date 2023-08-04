package com.example.data.di

import com.example.data.repository.CryptoCurrencyRepositoryImpl
import com.example.data.repository.FirebaseRepositoryImpl
import com.example.domain.datasource.local.CryptoCurrencyLocalDataSource
import com.example.domain.datasource.remote.CryptoCurrencyRemoteDataSource
import com.example.domain.datasource.remote.FirebaseRemoteDataSource
import com.example.domain.repository.CryptoCurrencyRepository
import com.example.domain.repository.FirebaseRepository
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
    fun provideCryptoCurrencyRepository(
        cryptoCurrencyRemoteDataSource: CryptoCurrencyRemoteDataSource,
        cryptoCurrencyLocalDataSource: CryptoCurrencyLocalDataSource
    ): CryptoCurrencyRepository {
        return CryptoCurrencyRepositoryImpl(
            cryptoCurrencyRemoteDataSource,
            cryptoCurrencyLocalDataSource
        )
    }

    @Singleton
    @Provides
    fun provideFirebaseRepository(firebaseRemoteDataSource: FirebaseRemoteDataSource): FirebaseRepository =
        FirebaseRepositoryImpl(firebaseRemoteDataSource)
}