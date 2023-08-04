package com.example.domain.usecase

import com.example.domain.model.FavoriteCrypto
import com.example.domain.model.Result

interface GetAllFavoritesUseCase {
    suspend operator fun invoke(): Result<List<FavoriteCrypto>>
}