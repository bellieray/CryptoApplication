package com.example.domain.usecase

import com.example.domain.model.CryptoDetail
import com.example.domain.repository.FirebaseRepository
import javax.inject.Inject

class AddToFavoriteUseCase @Inject constructor(private val firebaseRepository: FirebaseRepository) {
    suspend operator fun invoke(cryptoDetail: CryptoDetail) =
        firebaseRepository.addToFavorites(cryptoDetail)
}