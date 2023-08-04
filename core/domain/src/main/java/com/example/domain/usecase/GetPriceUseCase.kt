package com.example.domain.usecase

interface GetPriceUseCase {
    suspend operator fun invoke(coinId: String, refreshTime: String): com.example.domain.model.Result<Double>
}