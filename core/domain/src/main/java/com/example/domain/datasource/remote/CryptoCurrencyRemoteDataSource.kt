package com.example.domain.datasource.remote

import com.example.domain.model.Crypto
import com.example.domain.model.CryptoDetailResponse
import  com.example.domain.model.Result

interface CryptoCurrencyRemoteDataSource {
    suspend fun getAllCryptos(): Result<List<Crypto>>
    suspend fun getCryptoDetail(coinId: String): Result<CryptoDetailResponse>
    suspend fun getPrice(coinId: String, refreshTime: String): Result<Double>
    suspend fun detectPriceChange(): Crypto?
}