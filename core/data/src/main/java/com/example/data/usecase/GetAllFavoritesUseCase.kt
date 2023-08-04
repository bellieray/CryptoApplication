package com.example.data.usecase

import com.example.domain.repository.FirebaseRepository
import com.example.domain.usecase.GetAllFavoritesUseCase
import javax.inject.Inject

class GetAllFavoritesUseCaseImpl @Inject constructor(private val firebaseRepository: FirebaseRepository) : GetAllFavoritesUseCase {
    override suspend operator fun invoke() = firebaseRepository.getAllFavorites()
}