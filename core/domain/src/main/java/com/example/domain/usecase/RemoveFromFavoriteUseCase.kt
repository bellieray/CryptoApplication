package com.example.domain.usecase

interface RemoveFromFavoriteUseCase {
    suspend operator fun invoke(coinId: String): com.example.domain.model.Result<Unit>
}