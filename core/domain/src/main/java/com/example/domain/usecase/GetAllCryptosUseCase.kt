package com.example.domain.usecase

import com.example.domain.model.Crypto

interface GetAllCryptosUseCase {
    suspend operator fun invoke(): com.example.domain.model.Result<List<Crypto>>
}