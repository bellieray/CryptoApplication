package com.example.domain.repository

import com.example.domain.model.Crypto
import  com.example.domain.model.Result
import com.example.domain.model.CryptoDetailResponse


interface CryptoCurrencyRepository {
    suspend fun getAllCryptos(): Result<List<Crypto>>
    suspend fun getCryptoDetail(coinId: String): Result<CryptoDetailResponse>
    suspend fun getPrice(coinId: String, refreshTime: String): Result<Double>
    suspend fun getAllCryptosFromDb(): Result<List<Crypto>>
    suspend fun search(word: String): Result<List<Crypto>>
    suspend fun insertAll(infos: List<Crypto>): Result<Unit>
    suspend fun detectPriceChange(): Crypto?
}