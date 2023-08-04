package com.example.domain.usecase

import com.example.domain.model.Crypto

interface DetectPriceUseCase {
    suspend operator fun invoke(): Crypto?
}