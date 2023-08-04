package com.example.domain.usecase

import com.example.domain.model.CryptoDetail
import com.example.domain.model.Result

interface AddToFavoriteUseCase {
    suspend operator fun invoke(cryptoDetail: CryptoDetail): Result<Unit>
}