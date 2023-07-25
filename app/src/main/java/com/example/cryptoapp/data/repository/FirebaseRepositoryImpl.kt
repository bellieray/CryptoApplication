package com.example.cryptoapp.data.repository

import com.example.cryptoapp.domain.datasource.remote.FirebaseRemoteDataSource
import com.example.cryptoapp.domain.repository.FirebaseRepository
import com.example.cryptoapp.utils.Result
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseRepositoryImpl @Inject constructor(private val firebaseRemoteDataSource: FirebaseRemoteDataSource) :
    FirebaseRepository {
    override suspend fun login(email: String, password: String): Result<Unit> = firebaseRemoteDataSource.login(email, password)
    override suspend fun register(email: String, password: String): Result<Unit> = firebaseRemoteDataSource.register(email, password)
}