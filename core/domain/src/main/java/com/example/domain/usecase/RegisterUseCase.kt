package com.example.domain.usecase

import com.example.domain.repository.FirebaseRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val firebaseRepository: FirebaseRepository) {
    suspend operator fun invoke(email: String, password: String) =
        firebaseRepository.register(email, password)
}