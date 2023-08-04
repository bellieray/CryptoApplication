package com.example.data.di

import com.example.data.usecase.*
import com.example.domain.usecase.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCaseModule {
    @Binds
    @ViewModelScoped
    abstract fun bindAddToFavoriteUseCase(addToFavoriteUseCaseImpl: AddToFavoriteUseCaseImpl): AddToFavoriteUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindDetectPriceUseCase(detectPriceUseCaseImpl: DetectPriceUseCaseImpl): DetectPriceUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindGetAllCryptosUseCase(getAllCryptosUseCaseImpl: GetAllCryptosUseCaseImpl): GetAllCryptosUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindGetAllFavoritesUseCase(getAllFavoritesUseCaseImpl: GetAllFavoritesUseCaseImpl): GetAllFavoritesUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindGetCryptoDetailUseCase(getCryptoDetailUseCaseImpl: GetCryptoDetailUseCaseImpl): GetCryptoDetailUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindGetPriceUseCase(getPriceUseCaseImpl: GetPriceUseCaseImpl): GetPriceUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindInsertListToDbUseCase(insertListToDbUseCaseImpl: InsertListToDbUseCaseImpl): InsertListToDbUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindLoginUseCase(loginUseCaseImpl: LoginUseCaseImpl): LoginUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindRegisterUseCase(registerUseCaseImpl: RegisterUseCaseImpl): RegisterUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindRemoveFromFavoriteUseCase(removeFromFavoriteUseCaseImpl: RemoveFromFavoriteUseCaseImpl): RemoveFromFavoriteUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindSearchUseCase(searchUseCaseImpl: SearchUseCaseImpl): SearchUseCase

}