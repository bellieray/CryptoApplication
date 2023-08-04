package com.example.data.usecase

import com.example.domain.model.CryptoDetail
import com.example.domain.model.Result
import com.example.domain.repository.FirebaseRepository
import com.example.domain.usecase.AddToFavoriteUseCase
import javax.inject.Inject


class AddToFavoriteUseCaseImpl @Inject constructor(private val firebaseRepository: FirebaseRepository) :
    AddToFavoriteUseCase {
    override suspend operator fun invoke(cryptoDetail: CryptoDetail): Result<Unit> =
        firebaseRepository.addToFavorites(cryptoDetail)
}