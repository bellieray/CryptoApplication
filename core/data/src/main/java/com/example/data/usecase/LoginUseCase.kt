package com.example.data.usecase

import com.example.domain.repository.FirebaseRepository
import com.example.domain.usecase.LoginUseCase
import javax.inject.Inject

class LoginUseCaseImpl @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : LoginUseCase {
    override suspend operator fun invoke(email: String, password: String) =
        firebaseRepository.login(email, password)
}