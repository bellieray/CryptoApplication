package com.example.domain.datasource.local


import com.example.domain.model.Crypto
import  com.example.domain.model.Result

interface CryptoCurrencyLocalDataSource {
    suspend fun getAllCryptos():Result<List<Crypto>>
    suspend fun search(word: String): Result<List<Crypto>>
    suspend fun insertAll(infos: List<Crypto>): Result<Unit>
}