package com.example.domain.datasource.remote

import com.example.domain.model.CryptoDetail
import com.example.domain.model.FavoriteCrypto
import  com.example.domain.model.Result

interface FirebaseRemoteDataSource {
    suspend fun login(email: String, password: String): Result<Unit>
    suspend fun register(email: String, password: String): Result<Unit>
    suspend fun addToFavorites(cryptoDetail: CryptoDetail): Result<Unit>
    suspend fun removeFromFavorites(coinId: String): Result<Unit>
    suspend fun getAllFavorites() : Result<List<FavoriteCrypto>>
}