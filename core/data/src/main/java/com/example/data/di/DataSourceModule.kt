package com.example.data.di

import com.example.data.datasource.local.CoinsDAO
import com.example.data.datasource.local.CryptoCurrencyLocalDataSourceImpl
import com.example.data.datasource.remote.CryptoCurrencyRemoteDataSourceImpl
import com.example.data.datasource.remote.FirebaseRemoteDataSourceImpl
import com.example.domain.datasource.local.CryptoCurrencyLocalDataSource
import com.example.domain.datasource.remote.CryptoCurrencyRemoteDataSource
import com.example.domain.datasource.remote.FirebaseRemoteDataSource
import com.example.data.api.CryptoCurrencyService
import com.example.domain.repository.FirebaseRepository
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
    fun provideCryptoCurrencyRemoteDataSource(cryptoCurrencyService: CryptoCurrencyService, firebaseRepository: FirebaseRepository): CryptoCurrencyRemoteDataSource =
        CryptoCurrencyRemoteDataSourceImpl(cryptoCurrencyService, firebaseRepository)

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