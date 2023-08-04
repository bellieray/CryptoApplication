package com.example.domain.usecase

import com.example.domain.repository.FirebaseRepository
import javax.inject.Inject

class GetAllFavoritesUseCase @Inject constructor(private val firebaseRepository: FirebaseRepository) {
    suspend operator fun invoke() = firebaseRepository.getAllFavorites()
}