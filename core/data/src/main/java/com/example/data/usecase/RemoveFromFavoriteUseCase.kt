package com.example.data.usecase

import com.example.domain.repository.FirebaseRepository
import com.example.domain.usecase.RemoveFromFavoriteUseCase
import javax.inject.Inject

class RemoveFromFavoriteUseCaseImpl @Inject constructor(private val firebaseRepository: FirebaseRepository) :
    RemoveFromFavoriteUseCase {
    override suspend operator fun invoke(coinId: String): com.example.domain.model.Result<Unit> = firebaseRepository.removeFromFavorites(coinId)
}