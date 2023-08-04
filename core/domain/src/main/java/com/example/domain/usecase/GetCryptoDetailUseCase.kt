package com.example.domain.usecase

import com.example.domain.model.CryptoDetailResponse

interface GetCryptoDetailUseCase {
    suspend operator fun invoke(coinId: String): com.example.domain.model.Result<CryptoDetailResponse>
}