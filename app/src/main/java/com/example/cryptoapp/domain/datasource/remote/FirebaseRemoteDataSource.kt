package com.example.cryptoapp.domain.datasource.remote

import com.example.cryptoapp.utils.Result

interface FirebaseRemoteDataSource {
    suspend fun login(email: String, password: String): Result<Unit>
    suspend fun register(email: String, password: String): Result<Unit>
}