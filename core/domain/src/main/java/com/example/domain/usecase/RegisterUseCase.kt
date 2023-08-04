package com.example.domain.usecase

interface RegisterUseCase {
    suspend operator fun invoke(
        email: String,
        password: String
    ): com.example.domain.model.Result<Unit>
}