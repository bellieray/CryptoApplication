package com.example.cryptoapp.domain.repository

import com.example.cryptoapp.model.Crypto
import com.example.cryptoapp.model.CryptoDetailResponse
import com.example.cryptoapp.utils.Result


interface CryptoCurrencyRepository {
    suspend fun getAllCryptos(): Result<List<Crypto>>
    suspend fun getCryptoDetail(coinId: String): Result<CryptoDetailResponse>
    suspend fun getPrice(coinId: String, refreshTime: String): Result<Double>
    suspend fun getAllCryptosFromDb(): Result<List<Crypto>>
    suspend fun search(word: String): Result<List<Crypto>>
    suspend fun insertAll(infos: List<Crypto>): Result<Unit>
}