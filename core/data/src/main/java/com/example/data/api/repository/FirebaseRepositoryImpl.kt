package com.example.data.api.repository

import com.example.domain.datasource.remote.FirebaseRemoteDataSource
import com.example.domain.repository.FirebaseRepository
import com.example.cryptoapp.model.CryptoDetail
import com.example.domain.model.FavoriteCrypto
import javax.inject.Inject
import javax.inject.Singleton
import com.example.domain.model.Result

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