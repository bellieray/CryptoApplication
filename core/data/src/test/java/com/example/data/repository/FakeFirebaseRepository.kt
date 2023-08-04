package com.example.data.repository

import com.example.domain.model.CryptoDetail
import com.example.domain.model.FavoriteCrypto
import com.example.domain.model.Result
import com.example.domain.repository.FirebaseRepository

class FakeFirebaseRepository : FirebaseRepository {
    private var testRequest = TestResponseEnum.ERROR
    fun setRequest(testRequest: TestResponseEnum) {
        this.testRequest = testRequest
    }

    override suspend fun login(email: String, password: String): Result<Unit> {
        return when (testRequest) {
            TestResponseEnum.ERROR -> Result.Failed("error")
            TestResponseEnum.SUCCESS -> Result.Success()
        }
    }

    override suspend fun register(email: String, password: String): Result<Unit> {
        return when (testRequest) {
            TestResponseEnum.ERROR -> Result.Failed("error")
            TestResponseEnum.SUCCESS -> Result.Success()
        }
    }

    override suspend fun addToFavorites(cryptoDetail: CryptoDetail): Result<Unit> {
        return when (testRequest) {
            TestResponseEnum.ERROR -> Result.Failed("error")
            TestResponseEnum.SUCCESS -> Result.Success()
        }
    }

    override suspend fun removeFromFavorites(coinId: String): Result<Unit> {
        return when (testRequest) {
            TestResponseEnum.ERROR -> Result.Failed("error")
            TestResponseEnum.SUCCESS -> Result.Success()
        }
    }

    override suspend fun getAllFavorites(): Result<List<FavoriteCrypto>> {
        return when (testRequest) {
            TestResponseEnum.ERROR -> Result.Failed("error")
            TestResponseEnum.SUCCESS -> Result.Success()
        }
    }
}