package com.example.cryptoapp.data.datasource.remote

import com.example.cryptoapp.domain.datasource.remote.FirebaseRemoteDataSource
import com.example.cryptoapp.utils.Result
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseRemoteDataSourceImpl @Inject constructor(private val firebaseAuth: FirebaseAuth) :
    FirebaseRemoteDataSource {
    override suspend fun login(email: String, password: String): Result<Unit> {
        return try {
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            // If the authentication is successful, return Result.Success
            Result.Success()
        } catch (e: Exception) {
            // If an error occurs during authentication, return Result.Failed with the error message
            Result.Failed(e.message)
        }

    }

    override suspend fun register(email: String, password: String): Result<Unit> {
        return try {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
            Result.Success()
        } catch (e: Exception) {
            Result.Failed(e.localizedMessage.toString())
        }
    }
}