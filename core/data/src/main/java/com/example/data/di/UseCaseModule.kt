package com.example.data.di

import com.example.domain.repository.FirebaseRepository
import com.example.domain.usecase.AddToFavoriteUseCase
import com.example.domain.usecase.DetectPriceUseCase
import com.example.domain.usecase.GetAllCryptosUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {
    @Binds
    @ViewModelScoped
    abstract fun bindAddToFavoriteUseCase(firebaseRepository: FirebaseRepository): AddToFavoriteUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindDetectPriceUseCase(firebaseRepository: FirebaseRepository): DetectPriceUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindGetAllCryptosUseCase(firebaseRepository: FirebaseRepository): GetAllCryptosUseCase



}