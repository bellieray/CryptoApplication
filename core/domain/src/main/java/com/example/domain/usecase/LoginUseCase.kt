package com.example.domain.usecase

interface LoginUseCase {
    suspend operator fun invoke(
        email: String,
        password: String
    ): com.example.domain.model.Result<Unit>
}