package com.example.data.usecase

import com.example.domain.repository.FirebaseRepository
import com.example.domain.usecase.RegisterUseCase
import javax.inject.Inject

class RegisterUseCaseImpl @Inject constructor(private val firebaseRepository: FirebaseRepository) :
    RegisterUseCase {
    override suspend operator fun invoke(
        email: String,
        password: String
    ): com.example.domain.model.Result<Unit> =
        firebaseRepository.register(email, password)
}