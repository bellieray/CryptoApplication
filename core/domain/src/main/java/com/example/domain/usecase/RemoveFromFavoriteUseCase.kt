package com.example.domain.usecase

import com.example.domain.repository.FirebaseRepository
import javax.inject.Inject

class RemoveFromFavoriteUseCase @Inject constructor(private val firebaseRepository: FirebaseRepository) {
    suspend operator fun invoke(coinId: String) = firebaseRepository.removeFromFavorites(coinId)
}