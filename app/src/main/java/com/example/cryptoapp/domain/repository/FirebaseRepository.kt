package com.example.cryptoapp.domain.repository

import  com.example.cryptoapp.utils.Result

interface FirebaseRepository {
    suspend fun login(email: String, password: String): Result<Unit>
    suspend fun register(email: String, password: String): Result<Unit>
}