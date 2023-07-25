package com.example.cryptoapp.data.datasource.remote

import com.example.cryptoapp.domain.datasource.remote.FirebaseRemoteDataSource
import com.example.cryptoapp.model.CryptoDetail
import com.example.cryptoapp.model.FavoriteCrypto
import com.example.cryptoapp.utils.Constants.COINS
import com.example.cryptoapp.utils.Result
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseRemoteDataSourceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    val favoriteRef: CollectionReference
) :
    FirebaseRemoteDataSource {
    override suspend fun login(email: String, password: String): Result<Unit> {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Result.Success()
        } catch (e: Exception) {
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

    override suspend fun addToFavorites(cryptoDetail: CryptoDetail): Result<Unit> {
        return try {
            firebaseAuth.currentUser?.uid?.let {
                favoriteRef.document(it)
                    .collection(COINS).document(cryptoDetail.id ?: "")
                    .set(cryptoDetail).await()
            }
            Result.Success()
        } catch (e: FirebaseException) {
            Result.Failed(e.localizedMessage.toString())
        }
    }

    override suspend fun removeFromFavorites(coinId: String): Result<Unit> {
        return try {
            firebaseAuth.currentUser?.uid?.let {
                val coinRef = favoriteRef.document(it)
                    .collection(COINS).document(coinId).delete()
                coinRef.await()
            }
            Result.Success()
        } catch (e: FirebaseException) {
            Result.Failed(e.localizedMessage)
        }
    }

    override suspend fun getAllFavorites(): Result<List<FavoriteCrypto>> {
        return try {
            firebaseAuth.currentUser?.uid?.let { userId ->
                val snapshot = favoriteRef.document(userId)
                    .collection(COINS).get().await()
                val data = FavoriteCrypto.from(snapshot)
                Result.Success(data)
            } ?: Result.Failed("User is not authenticated.")
        } catch (e: FirebaseException) {
            Result.Failed(e.localizedMessage.toString())
        }
    }

}