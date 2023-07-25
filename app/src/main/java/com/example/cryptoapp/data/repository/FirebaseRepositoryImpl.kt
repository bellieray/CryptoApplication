package com.example.cryptoapp.data.repository

import com.example.cryptoapp.domain.datasource.remote.FirebaseRemoteDataSource
import com.example.cryptoapp.domain.repository.FirebaseRepository
import com.example.cryptoapp.model.CryptoDetail
import com.example.cryptoapp.model.FavoriteCrypto
import com.example.cryptoapp.utils.Result
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseRepositoryImpl @Inject constructor(private val firebaseRemoteDataSource: FirebaseRemoteDataSource) :
    FirebaseRepository {
    override suspend fun login(email: String, password: String): Result<Unit> =
        firebaseRemoteDataSource.login(email, password)

    override suspend fun register(email: String, password: String): Result<Unit> =
        firebaseRemoteDataSource.register(email, password)

    override suspend fun addToFavorites(cryptoDetail: CryptoDetail): Result<Unit> =
        firebaseRemoteDataSource.addToFavorites(cryptoDetail)

    override suspend fun removeFromFavorites(coinId: String): Result<Unit> =
        firebaseRemoteDataSource.removeFromFavorites(coinId)

    override suspend fun getAllFavorites(): Result<List<FavoriteCrypto>> =
        firebaseRemoteDataSource.getAllFavorites()
}