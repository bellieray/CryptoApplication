package com.example.cryptoapp.di

import com.example.cryptoapp.api.CryptoCurrencyService
import com.example.cryptoapp.data.datasource.local.CoinsDAO
import com.example.cryptoapp.data.datasource.local.CryptoCurrencyLocalDataSourceImpl
import com.example.cryptoapp.data.datasource.remote.CryptoCurrencyRemoteDataSourceImpl
import com.example.cryptoapp.data.datasource.remote.FirebaseRemoteDataSourceImpl
import com.example.cryptoapp.domain.datasource.local.CryptoCurrencyLocalDataSource
import com.example.cryptoapp.domain.datasource.remote.CryptoCurrencyRemoteDataSource
import com.example.cryptoapp.domain.datasource.remote.FirebaseRemoteDataSource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
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
    fun provideCryptoCurrencyLocalDataSource(coinsDAO: CoinsDAO): CryptoCurrencyLocalDataSource =
        CryptoCurrencyLocalDataSourceImpl(coinsDAO)

    @Singleton
    @Provides
    fun provideFirebaseRemoteDataSource(
        auth: FirebaseAuth,
        firestoreReference: CollectionReference
    ): FirebaseRemoteDataSource =
        FirebaseRemoteDataSourceImpl(auth, firestoreReference)
}