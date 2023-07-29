package com.example.data.datasource.local

import com.example.domain.datasource.local.CryptoCurrencyLocalDataSource
import com.example.domain.model.Crypto
import javax.inject.Inject
import  com.example.domain.model.Result
class CryptoCurrencyLocalDataSourceImpl @Inject constructor(val coinsDAO: CoinsDAO) :
    CryptoCurrencyLocalDataSource {
    override suspend fun getAllCryptos(): Result<List<Crypto>> {
        return try {
            val response = coinsDAO.getAll()
            Result.Success(response)
        } catch (e: Exception) {
            Result.Failed(e.localizedMessage)
        }
    }

    override suspend fun search(word: String): Result<List<Crypto>> {
        return try {
            val response = coinsDAO.search(word)
            Result.Success(response)
        } catch (e: Exception) {
            Result.Failed(e.localizedMessage)
        }
    }

    override suspend fun insertAll(infos: List<Crypto>): Result<Unit> {
        return try {
            coinsDAO.insertAll(infos)
            Result.Success()
        } catch (e: Exception) {
            Result.Failed(e.localizedMessage)
        }
    }
}