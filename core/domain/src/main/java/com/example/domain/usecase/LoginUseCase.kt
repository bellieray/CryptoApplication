package com.example.domain.usecase

import com.example.domain.repository.FirebaseRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) {
    suspend operator fun invoke(email: String, password: String) =
        firebaseRepository.login(email, password)
}